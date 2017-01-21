package nl.tisit.strava.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "club")
public class Club {
	@Id
	private Integer id;
	@JsonProperty("resource_state")
	private Integer resourceState;	
	private String name;
	@JsonProperty("profile_medium")
	private String profileMedium;
	private String profile;
	@JsonProperty("cover_photo")
	private String coverPhoto;
	private String description;
//	club_type:	string 
//	casual_club, racing_team, shop, company, other
//	sport_type:	string 
//	cycling, running, triathlon, other
	private String city;
	private String state;
	private String country;
	@JsonProperty("private")
	private Boolean privateClub;
	@JsonProperty("member_count")
	private Integer memberCount;
	private Boolean featured;
	private MembershipStatus membership; 
	private Boolean admin;
	private Boolean owner; 
	@JsonProperty("following_count")
	private Integer followingCount;
	private String url;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getResourceState() {
		return resourceState;
	}
	public void setResourceState(Integer resourceState) {
		this.resourceState = resourceState;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getCoverPhoto() {
		return coverPhoto;
	}
	public void setCoverPhoto(String coverPhoto) {
		this.coverPhoto = coverPhoto;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public Boolean getPrivateClub() {
		return privateClub;
	}
	public void setPrivateClub(Boolean privateClub) {
		this.privateClub = privateClub;
	}
	public Integer getMemberCount() {
		return memberCount;
	}
	public void setMemberCount(Integer memberCount) {
		this.memberCount = memberCount;
	}
	public Boolean getFeatured() {
		return featured;
	}
	public void setFeatured(Boolean featured) {
		this.featured = featured;
	}
	public MembershipStatus getMembership() {
		return membership;
	}
	public void setMembership(MembershipStatus membership) {
		this.membership = membership;
	}
	public Boolean getAdmin() {
		return admin;
	}
	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}
	public Boolean getOwner() {
		return owner;
	}
	public void setOwner(Boolean owner) {
		this.owner = owner;
	}
	public Integer getFollowingCount() {
		return followingCount;
	}
	public void setFollowingCount(Integer followingCount) {
		this.followingCount = followingCount;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "Club [id=" + id + ", resourceState=" + resourceState + ", name=" + name + ", profileMedium="
				+ profileMedium + ", profile=" + profile + ", coverPhoto=" + coverPhoto + ", description=" + description
				+ ", city=" + city + ", state=" + state + ", country=" + country + ", privateClub=" + privateClub
				+ ", memberCount=" + memberCount + ", featured=" + featured + ", membership=" + membership + ", admin="
				+ admin + ", owner=" + owner + ", followingCount=" + followingCount + ", url=" + url + "]";
	} 

	
}
