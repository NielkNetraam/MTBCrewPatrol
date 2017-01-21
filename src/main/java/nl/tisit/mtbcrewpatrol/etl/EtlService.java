package nl.tisit.mtbcrewpatrol.etl;

public interface EtlService {
	public void exportTracks();
	public void exportSegments();
	public void exportWildRides();
	public void exportMarkers();
	public void exportAggregate(ExportFormat format);
}
