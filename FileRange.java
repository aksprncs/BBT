
class FileRange {
	String filename;
	Long startOffset;
	Long endOffset;
	
	public FileRange(String filename,Long startOffset,Long endOffset)
	{
		this.filename = filename;
		this.startOffset = startOffset;
		this.endOffset = endOffset;
	}

}
