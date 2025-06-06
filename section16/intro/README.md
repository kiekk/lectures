# ☘️ 개요

---

## 📖 내용

- Multipart 는 일반 텍스트 형식이 아니라 하나의 HTTP 요청/응답 바디 내에서 여러 개의 파트를 나누어 전송하는 형식으로서 파일 업로드나 여러 데이터 파트(텍스트 파트, 바이너리 파트 등)
  를 함께 전송해야 할 때 주로 사용한다
- 웹 브라우저에서 <form> 태그에 enctype="multipart/form-data" 를 지정하고 파일 업로드를 수행하면 ‘multipart/form-data’ 형식의 HTTP 요청이 전송 된다

---

### 파트 구분
- 텍스트(Form 필드) 파트
------WebKitFormBoundaryABC123 Content-Disposition: form-data; name="username" alice
  - name="username" : 필드 이름(username)
  - 실제 데이터: alice
  - 이 부분은 별도의 파일이 아닌 일반 폼 필드 텍스트
2. 파일 파트
   ------WebKitFormBoundaryABC123 Content-Disposition: form-data; name="file";
   filename="profile.jpg" Content-Type: image/jpeg ÿØÿà ..JFIF....<바이너리 데이터>...
   - name="file" : 업로드되는 파일 필드 이름
   - filename="profile.jpg" : 업로드 파일의 원본 파일명
   - Content-Type: image/jpeg : MIME 타입(이미지)
   - 이후 줄부터는 실제 바이너리 파일 데이터가 들어 있음
3. 종료선 (boundary 마무리)
   ------WebKitFormBoundaryABC123--
   - 마지막 구분선 뒤에 --가 붙어 multipart 데이터가 종료됨을 알림

---

### MultiPart 지원 도구
- Spring(특히 Spring Boot 3.x 기준)에서 Multipart(파일 업로드)를 처리하기 위한 도구로서 MultipartResolver, MultipartFile, MultipartHttpServletRequest 그리고 이를 사용하는 Controller/Service 구조가 유기적으로 연결되어 있다


- MultipartAutoConfiguration
  - Spring Boot 에서 multipart/form-data 요청 처리를 자동으로 구성해주는 설정 클래스로서 추가적인 설정 없이도 @RequestParam("file") MultipartFile file 을 사용하면 자동으로 멀티파트 요청을 처리하도록 구성된다
- MultipartHttpServletRequest
  - HttpServletRequest를 상속(혹은 래핑)하여 멀티파트 폼 데이터를 처리할 수 있는 추가 메서드를 제공하는 인터페이스로서 기본 구현제로 StandardMultipartHttpServletRequest 클래스가 제공된다
- MultipartFile
  - 업로드된 파일을 다루기 위한 인터페이스로서 getName(), getOriginalFilename(), getSize(), transferTo(File dest) 와 같은 API 가 있다
- MultipartResolver
  - multipart/form-data 요청을 해석하여 MultipartHttpServletRequest 를 만들어주는 인터페이스로서 기본 구현제로 StandardServletMultipartResolver 가 제공된다
- MultipartProperties
  - Spring Boot 에서 멀티파트 설정을 위한 구성 설정 클래스이다
- @RequestPart
  - multipart 요청의 특정 파트를 직접 바인딩하기 위한 어노테이션으로서 @RequestParam 보다 좀 더 확장된 기능을 제공한다

---

## 🔍 중심 로직

```java
package org.springframework.web.multipart;

// imports

public interface MultipartFile extends InputStreamSource {

	String getName();

	@Nullable
	String getOriginalFilename();

	@Nullable
	String getContentType();

	boolean isEmpty();

	long getSize();

	byte[] getBytes() throws IOException;

	@Override
	InputStream getInputStream() throws IOException;

	default Resource getResource() {
		return new MultipartFileResource(this);
	}

	void transferTo(File dest) throws IOException, IllegalStateException;

	default void transferTo(Path dest) throws IOException, IllegalStateException {
		FileCopyUtils.copy(getInputStream(), Files.newOutputStream(dest));
	}

}
```

📌

---

## 💬 코멘트

---
