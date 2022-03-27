package Business;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

public class ExprerimentCSVWritter {
	private List<String> headers;
	private List<List<String>> lines;
	private String path;
	private String fileName;
	
	public ExprerimentCSVWritter(List<String> headers, List<List<String>> lines, String path, String fileName) {
		super();
		this.headers = headers;
		this.lines = lines;
		this.path = path;
		this.fileName = fileName;
	}
	
	public void WriteData()
	{
		try {
			String absolutPath = "";
			if (path == null) {
				absolutPath = fileName;
			} else {
				absolutPath = path + "\\" + fileName;
			}
			
			File csvFile = new File(absolutPath);
			boolean isFirstExeperiment = !csvFile.exists();
			FileWriter csvwriter = new FileWriter(csvFile, true);
			if(isFirstExeperiment)
			{
				for(String header : headers)
				{
					 csvwriter.append(header);
					 csvwriter.append(";");
				}
				csvwriter.append("\n");
			}
			
			for(List<String> line : lines)
			{
				for(String data : line)
				{
					 csvwriter.append(data);
					 csvwriter.append(";");
				}
				csvwriter.append("\n");
			}
			csvwriter.close();
			System.out.println(csvFile.getAbsolutePath());
        } catch (Exception e) {
            System.out.println("exception :" + e.getMessage());
        }
	}
	
	
	
	
	
	

}
