package mongo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
	private String userId;
	private List<String> subscribedRepoIds;
	private List<String> subscribedUserIds;
	private int intervalTimeInMinutes;
}
