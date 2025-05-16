## jpa show_sql 성능 최적화

### JPA 쿼리 출력
- JPA의 show_sql 속성을 사용하면 쿼리를 콘솔에서 확인할 수 있으며,
- format_sql 속성을 사용하면 쿼리를 가독성있게 포맷팅하여 확인할 수 있다.

```yaml
spring:
  jpa:
    show-sql: true
    properties:
      hibernate:
        format_sql: true
```

### show_sql 성능 최적화
- 하지만 show_sql을 사용하면 성능이 저하될 수 있다.
- 이는 JPA의 쿼리를 출력하는 객체의 동작 방식을 확인해보면 쉽게 파악할 수 있습니다.

- hibernate에서는 `SqlStatementLogger`, `SqlStatementLoggerInitiator` 클래스를 사용하여 쿼리 로그를 출력합니다.
  - `SqlStatementLoggerInitiator`는 작성한 yml에 jpa 속성으로 `SqlStatementLogger`를 초기화하는 클래스입니다.
  - `SqlStatementLogger`는 실제 쿼리 로그를 출력하는 객체입니다.

```java
package org.hibernate.engine.jdbc.internal;

...

public class SqlStatementLoggerInitiator implements StandardServiceInitiator<SqlStatementLogger> {

	// this deprecated property name never respected our conventions
	private static final String OLD_LOG_SLOW_QUERY = "hibernate.session.events.log.LOG_QUERIES_SLOWER_THAN_MS";

	public static final SqlStatementLoggerInitiator INSTANCE = new SqlStatementLoggerInitiator();

	@Override
	public SqlStatementLogger initiateService(Map<String, Object> configValues, ServiceRegistryImplementor registry) {
		final boolean showSQL = getBoolean( SHOW_SQL, configValues );
		final boolean formatSQL = getBoolean( FORMAT_SQL, configValues );
		final boolean highlightSQL = getBoolean( HIGHLIGHT_SQL, configValues );

		long logSlowQuery = getLong( LOG_SLOW_QUERY, configValues, -2 );
		if ( logSlowQuery == -2 ) {
			logSlowQuery = getLong( OLD_LOG_SLOW_QUERY, configValues, 0 );
		}

        // 여기서 SqlStatementLogger 객체를 생성합니다.
		return new SqlStatementLogger( showSQL, formatSQL, highlightSQL, logSlowQuery );
	}

	@Override
	public Class<SqlStatementLogger> getServiceInitiated() {
		return SqlStatementLogger.class;
	}
}
```

```java
package org.hibernate.engine.jdbc.spi;

...

public class SqlStatementLogger implements Service {
	private static final Logger LOG = CoreLogging.logger( "org.hibernate.SQL" );
	private static final Logger LOG_SLOW = CoreLogging.logger( "org.hibernate.SQL_SLOW" );

	private final boolean logToStdout;
	private final boolean format;
	private final boolean highlight;

	/**
	 * Configuration value that indicates slow query. (In milliseconds) 0 - disabled.
	 */
	private final long logSlowQuery;

    ... overloading constructors

	/**
	 * Constructs a new {@code SqlStatementLogger} instance.
	 *
	 * @param logToStdout Should we log to STDOUT in addition to our internal logger?
	 * @param format Should we format the statements in the console and log
	 * @param highlight Should we highlight the statements in the console
	 * @param logSlowQuery Should we logs query which executed slower than specified milliseconds. 0 - disabled.
	 */
	public SqlStatementLogger(boolean logToStdout, boolean format, boolean highlight, long logSlowQuery) {
		this.logToStdout = logToStdout;
		this.format = format;
		this.highlight = highlight;
		this.logSlowQuery = logSlowQuery;
	}

    ... other methods

	/**
	 * Log a SQL statement string.
	 *
	 * @param statement The SQL statement.
	 */
	public void logStatement(String statement) {
		// for now just assume a DML log for formatting
		logStatement( statement, FormatStyle.BASIC.getFormatter() );
	}

	/**
	 * Log a SQL statement string using the specified formatter
	 *
	 * @param statement The SQL statement.
	 * @param formatter The formatter to use.
	 */
	@AllowSysOut
	public void logStatement(String statement, Formatter formatter) {
		if ( !logToStdout && !LOG.isDebugEnabled() ) {
			return;
		}

		try {
			if ( format ) {
				statement = formatter.format( statement );
			}
			if ( highlight ) {
				statement = FormatStyle.HIGHLIGHT.getFormatter().format( statement );
			}
		}
		catch (RuntimeException ex) {
			LOG.warn( "Couldn't format statement", ex );
		}

		LOG.debug( statement );
        
        // show_sql 속성이 true일 경우 콘솔에 쿼리를 출력합니다.
		if ( logToStdout ) {
			String prefix = highlight ? "\u001b[35m[Hibernate]\u001b[0m " : "Hibernate: ";
            // 출력 시 System.out.println을 사용하여 콘솔에 출력합니다.
			System.out.println( prefix + statement );
		}
	}

    ... other methods

}
```

- 이 때, `show_sql` 속성이 `true` 일 경우 `System.out.println`을 사용하여 콘솔에 쿼리를 출력합니다.
```java
// SqlStatementLogger.java
if ( logToStdout ) {
    String prefix = highlight ? "\u001b[35m[Hibernate]\u001b[0m " : "Hibernate: ";
    // 출력 시 System.out.println을 사용하여 콘솔에 출력합니다.
    System.out.println( prefix + statement );
}
```
- 따라서 `show_sql`을 사용하면 성능이 저하가 된다는 얘기는 내부적으로 `System.out.println`을 사용하여 쿼리를 출력하기 때문에 성능이 저하가 된다는 것입니다.


### 해결
- `show_sql`을 사용하지 않고 Logger를 사용하여 쿼리를 출력하면 됩니다.
```yaml
spring:
  jpa:
    show-sql: false # 비활성화
    properties:
      hibernate:
        format_sql: false # 비활성화
```
- Spring에서 기본적으로 지원하는 `logback`을 사용하여 쿼리를 출력할 수 있으며,
- JPA 특성상 쿼리 포맷, 바인딩 파라미터등이 가독성있게 출력되지 않기 때문에 `Slf4jSpyLogDelegator`까지 같이 사용해서 쿼리 로그 출력을 개선할 수 있습니다.
- 이렇게 작성하게 될 경우 더 이상 `System.out.println`을 사용하지 않기 때문에 성능 저하가 발생하지 않습니다.

```properties
log4jdbc.spylogdelegator.name=net.sf.log4jdbc.log.slf4j.Slf4jSpyLogDelegator
log4jdbc.dump.sql.maxlinelength=0
log4j.category.com.zaxxer.hikari=INFO
log4j.logger.HikariPool=INFO
log4jdbc.drivers=com.mysql.cj.jdbc.Driver
log4jdbc.auto.load.popular.drivers=false
```

```yaml
spring:
  datasource:
    driver-class-name: net.sf.log4jdbc.sql.jdbcapi.DriverSpy
    url: jdbc:log4jdbc:mysql://127.0.0.1:3306/article
```