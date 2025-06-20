package memory;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Memory {
    private long used;
    private long max;
}
