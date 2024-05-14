package b172.challenging.app.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "app_notification_template")
@NoArgsConstructor
public class AppNotificationTemplate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "app_notification_template_id")
	private Long id;

	@Column(nullable = false, length = 50)
	@Enumerated(EnumType.STRING)
	private AppNotificationType type;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String content;
}
