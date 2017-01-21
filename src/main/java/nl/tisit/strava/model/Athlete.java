package nl.tisit.strava.model;

import java.time.ZonedDateTime;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "athlete")
public class Athlete {
	@Id
	private Integer id;
	@Column(name = "resource_state")
	@JsonProperty("resource_state")
	private Integer resourceState;
	@Column(name = "firstname")
	@JsonProperty("firstname")
	private String firstName;
	@Column(name = "lastname")
	@JsonProperty("lastname")
	private String lastName;
  	@Column(name = "profile_medium")
	@JsonProperty("profile_medium")
	private String profileMedium;
	private String profile;
	private String city;
	private String state;
	private String country;
	@JsonProperty("sex")
	private Gender gender;
	private FollowingStatus friend;
	private FollowingStatus follower;
	private Boolean premium;
	@Column(name = "created_at")
	@JsonProperty("created_at")
	ZonedDateTime createdAt;
	@Column(name = "updated_at")
	@JsonProperty("updated_at")
	ZonedDateTime updatedAt;
	@Column(name = "follower_count")
	@JsonProperty("follower_count")
	private Integer followerCount;
	@Column(name = "friend_count")
	@JsonProperty("friend_count")
	private Integer friendCount;
	@Column(name = "mutual_friend_count")
	@JsonProperty("mutual_friend_count")
	private Integer mutualFriendCount;
	@Column(name = "athlete_type")
	@JsonProperty("athlete_type")
	private AthleteType athleteType;
	@Column(name = "date_preference")
	@JsonProperty("date_preference")
	private String datePreference;
	@Column(name = "measurement_preference")
	@JsonProperty("measurement_preference")
	private Measurement measurementPreference;
	private String email;
	private Integer ftp;
	private Float weight;
	@Column(name = "username")
	@JsonProperty("username")
	private String userName;
	@Column(name = "badge_type_id")
	@JsonProperty("badge_type_id")
	private Integer badgeTypeId;

	@ElementCollection
	private List<Club> clubs;
	@ElementCollection
	@CollectionTable(name = "athlete_bike")
	private List<Gear> bikes;
	@ElementCollection
	@CollectionTable(name = "athlete_shoes")
	private List<Gear> shoes;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getBadgeTypeId() {
		return badgeTypeId;
	}

	public void setBadgeTypeId(Integer badgeTypeId) {
		this.badgeTypeId = badgeTypeId;
	}

	
	public Integer getResourceState() {
		return resourceState;
	}

	public void setResourceState(Integer resourceState) {
		this.resourceState = resourceState;
	}

	public String getProfileMedium() {
		return profileMedium;
	}

	public void setProfileMedium(String profileMedium) {
		this.profileMedium = profileMedium;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public FollowingStatus getFriend() {
		return friend;
	}

	public void setFriend(FollowingStatus friend) {
		this.friend = friend;
	}

	public FollowingStatus getFollower() {
		return follower;
	}

	public void setFollower(FollowingStatus follower) {
		this.follower = follower;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Boolean getPremium() {
		return premium;
	}

	public void setPremium(Boolean premium) {
		this.premium = premium;
	}
	
	public Integer getFollowerCount() {
		return followerCount;
	}

	public void setFollowerCount(Integer followerCount) {
		this.followerCount = followerCount;
	}

	public Integer getFriendCount() {
		return friendCount;
	}

	public void setFriendCount(Integer friendCount) {
		this.friendCount = friendCount;
	}

	public Integer getMutualFriendCount() {
		return mutualFriendCount;
	}

	public void setMutualFriendCount(Integer mutualFriendCount) {
		this.mutualFriendCount = mutualFriendCount;
	}

	public AthleteType getAthleteType() {
		return athleteType;
	}

	public void setAthleteType(AthleteType athleteType) {
		this.athleteType = athleteType;
	}

	public String getDatePreference() {
		return datePreference;
	}

	public void setDatePreference(String datePreference) {
		this.datePreference = datePreference;
	}

	public Measurement getMeasurementPreference() {
		return measurementPreference;
	}

	public void setMeasurementPreference(Measurement measurementPreference) {
		this.measurementPreference = measurementPreference;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getFtp() {
		return ftp;
	}

	public void setFtp(Integer ftp) {
		this.ftp = ftp;
	}

	public Float getWeight() {
		return weight;
	}

	public void setWeight(Float weight) {
		this.weight = weight;
	}

	public List<Gear> getBikes() {
		return bikes;
	}

	public void setBikes(List<Gear> bikes) {
		this.bikes = bikes;
	}

	public List<Gear> getShoes() {
		return shoes;
	}

	public void setShoes(List<Gear> shoes) {
		this.shoes = shoes;
	}

	public List<Club> getClubs() {
		return clubs;
	}

	public void setClubs(List<Club> clubs) {
		this.clubs = clubs;
	}

	public ZonedDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(ZonedDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public ZonedDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(ZonedDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "Athlete [id=" + id + ", resourceState=" + resourceState + ", firstName=" + firstName + ", lastName="
				+ lastName + ", profileMedium=" + profileMedium + ", profile=" + profile + ", city=" + city + ", state="
				+ state + ", country=" + country + ", gender=" + gender + ", friend=" + friend + ", follower="
				+ follower + ", premium=" + premium + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
				+ ", followerCount=" + followerCount + ", friendCount=" + friendCount + ", mutualFriendCount="
				+ mutualFriendCount + ", athleteType=" + athleteType + ", datePreference=" + datePreference
				+ ", measurementPreference=" + measurementPreference + ", email=" + email + ", ftp=" + ftp + ", weight="
				+ weight + ", userName=" + userName + ", badgeTypeId=" + badgeTypeId + ", clubs=" + clubs + ", bikes="
				+ bikes + ", shoes=" + shoes + "]";
	}

}

