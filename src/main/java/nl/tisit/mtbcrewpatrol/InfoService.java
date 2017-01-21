package nl.tisit.mtbcrewpatrol;

public interface InfoService {
	void segmentOnTrack(int maxDistance);
	void segmentNotOnTrack(int maxDistance);
	void segmentWild();
}
