import java.util.*;





public class ThreadDistribution {
	
	public static void main(String args[])
	{
		//this can be a driver function
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter no of files");
		
		String s = scanner.nextLine(); 
		int n =Integer.parseInt(s);
		List<File> files= new ArrayList<File>();
		
		String fileNamePrefix = "File";
		
		
		System.out.println("Enter " + n + " values(one value per line) denoting the no of bytes in each file");
		
		
		for(int i =0;i<n;i++)
		{
			
			s = scanner.nextLine(); 
			long b =Integer.parseInt(s);
			
			File file = new File(fileNamePrefix+(i+1),b);
			files.add(file);
		}		
		
		System.out.println("Enter no of threads");
		s = scanner.nextLine(); 
		int t =Integer.parseInt(s);
		
		List<List<FileRange>> split =  getSplits(files,t);
		
		printAns(split);
		
	}
	
	static List<List<FileRange>> getSplits(List<File> files, int N)
	{
		List<List<FileRange>> ans = null;
		
		long totalBytes = 0;
		for(int i=0;i<files.size();i++)
		{
			totalBytes+=files.get(i).length;
		}
		
//		System.out.println("Total bytes are " + totalBytes);
		
		long X = -1, P = -1;
		for(int p =0;p<=N;p++)
		{
			//check if this is a valid value of P
			double x =  (totalBytes -(double)p) / (double)(N);
			if(x<0)
				continue;
			
			//see if x is a integer value
			if(x==Math.ceil(x))
			{
				//X is valid
				X = (long)x;
				P =p;
				break;
			}
		}
		
		if(X == -1 || P == -1)
		{
			//didn't find a possible combination
			return null;
		}
		
//		System.out.println("X is " + X + " P is: " + P);
		
		
		//now (N-P)  thread will get (X)  bytes and  P threads will get (X+1) bytes.
		long T1 = N - P;
		long T2 = P;
		
		long X1 = X;
		long X2 = X+1;
		
		
//		System.out.println(T1 + " threads will get " + X1 + " bytes and " + T2 + " threads will get " + X2 + " bytes");
		
		//now we will create segments for each thread
		
		ans = new ArrayList<List<FileRange>>();
		
		int fileNo = 0;
		long threadCount=1;
		long byteStartIndex = 0;
		//First T1 thread will be taking X1 bytes and T2 threads will be taking X2 bytes
		
		while(threadCount<=N && fileNo<files.size())
		{
			List<FileRange> listOfFileRangesForCurrentThread = new ArrayList<FileRange>();
			
			long totalBytesNeededByCurrentThread;
			
			if(threadCount<=T1)
			{
				totalBytesNeededByCurrentThread = X1;
			}
			else
			{
				totalBytesNeededByCurrentThread = X2;
			}
			
			while(totalBytesNeededByCurrentThread>0 && fileNo<files.size())
			{
				File curFile = files.get(fileNo);
				long curFileEndByteIndex = curFile.length-1;
				long totalBytesAvailableInThisFile = (curFileEndByteIndex - byteStartIndex +1);
				
				if(totalBytesAvailableInThisFile == totalBytesNeededByCurrentThread)
				{
					FileRange fileRange  = new FileRange(curFile.filename,byteStartIndex,curFileEndByteIndex);
					listOfFileRangesForCurrentThread.add(fileRange);
					fileNo++;
					threadCount++;
					byteStartIndex = 0;
					totalBytesNeededByCurrentThread = 0; //no more bytes needed for current thread
				}
				
				else if(totalBytesAvailableInThisFile > totalBytesNeededByCurrentThread)
				{
					FileRange fileRange  = new FileRange(curFile.filename,byteStartIndex,byteStartIndex+totalBytesNeededByCurrentThread-1);
					listOfFileRangesForCurrentThread.add(fileRange);
					threadCount++;
					byteStartIndex = byteStartIndex + totalBytesNeededByCurrentThread;
					totalBytesNeededByCurrentThread = 0;
				}
				
				else
				{
					FileRange fileRange  = new FileRange(curFile.filename,byteStartIndex,curFileEndByteIndex);
					listOfFileRangesForCurrentThread.add(fileRange);
					totalBytesNeededByCurrentThread = totalBytesNeededByCurrentThread - (curFileEndByteIndex-byteStartIndex+1); 
					byteStartIndex = 0;
					fileNo++;
				}
			}	
			ans.add(listOfFileRangesForCurrentThread);
		}
		return ans;
	}
	
	
	static void printAns(List<List<FileRange>> threadDistribution)
	{
		for(int i=0;i<threadDistribution.size();i++)
		{
			List<FileRange> fileRanges =  threadDistribution.get(i);
			System.out.println(" \n For thread:" + (i+1) + "\n");
			
			
			for(int j=0;j<fileRanges.size();j++)
			{
				FileRange fileRange  = fileRanges.get(j);
				System.out.println(fileRange.filename + " " + fileRange.startOffset + " " + fileRange.endOffset);
			}
			
		}
		
		
		
	}
}
