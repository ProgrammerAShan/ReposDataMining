import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import Config.Conf;
import PositionProcess.StressMajorization.Floyd;
import Statistic.NetWorkMeasurement;
import Tool.AdjacementMatrix;
import Tool.DbToNodeRelation;
import Tool.WeightAdjacementMatrix;

/**
 * @author weitao
 * 
 */
public class NetWorkMeasurementTest
{

	/**
	 * @param args
	 *            暂时没有参数
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException
	{
		int nodenumber=500000;
		int guli=0;
		String isConnectedString="connected";
		String GraphFilepath=Conf.GetGraphDir()+"/"+Integer.toString(nodenumber)+"/"+isConnectedString+"/NodeMatrix.txt";
	//	String UserDegreeFilePath=Conf.GetDataDir()+"/"+Integer.toString(nodenumber)+"/"+isConnectedString+"/UserDegree.txt";
		HashMap<Integer, TreeSet<Integer>> edgeMap = new HashMap<Integer, TreeSet<Integer>>();
//			File UserDegreeFile = new File(UserDegreeFilePath);   
//	        if (!UserDegreeFile.exists()) {   
//	        	UserDegreeFile.createNewFile();   
//	        }   
//	        OutputStreamWriter UserDegreewrite = new OutputStreamWriter(new FileOutputStream(UserDegreeFile),"GB2312");   
//	        BufferedWriter UserDegreewriter=new BufferedWriter(UserDegreewrite);
		int nodeindex=0;
	    int userindex=0;
		try
		{
			File f = new File(GraphFilepath);
			if (f.isFile() && f.exists())
			{
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(f), "UTF-8");
				BufferedReader reader = new BufferedReader(read);
				String line;
		//		int guli=0;

				while ((line = reader.readLine()) != null)
				{
					nodeindex++;
					if(nodeindex%10000==0)
					{
						System.out.println("读取到"+nodeindex+"个节点！");
					}
					userindex++;
					String[] item = line.split(":");
					if (item.length != 2)
					{
						guli++;
						continue;
					} else
					{
						String[] neibornumber = item[1].split(" ");
						int length = neibornumber.length;
						if (length != 0)
						{
							TreeSet<Integer> treeSet = new TreeSet<Integer>();
							for (int i = 0; i < length; i++)
							{
								treeSet.add(Integer.parseInt(neibornumber[i]));
							}
							edgeMap.put(Integer.parseInt(item[0]), treeSet);
//							UserDegreewriter.write(Integer.toString(userindex)+" "+Integer.toString(treeSet.size()));
//							UserDegreewriter.append("\n");
//							UserDegreewriter.flush();
							
  
						}
					}

				}
	//			UserDegreewriter.close();
				System.out.println("孤立点："+guli);
				read.close();
			}
		} catch (Exception e)
		{
			System.out.println("读取文件内容操作出错");
			e.printStackTrace();
		}
		AdjacementMatrix nodeMatrix=new AdjacementMatrix(edgeMap, nodenumber);
		NetWorkMeasurement nt=new NetWorkMeasurement(nodeMatrix,nodenumber,isConnectedString,guli);
		nt.InitializeProcess();
		nt.getstatic();
	//	nt.getDegreeX();
	//	nt.makeDegreeMatrix();
//		nt.GetDegreeAndClusterCoefficient();
//		nt.GetDegreeCorrelation();
//		nt.getPtscoefficient();
//		nt.getMixingMatrix();
//		nt.GetReciprocity();
		nt.GetSeperationDegree();

//		WeightAdjacementMatrix matrix=new WeightAdjacementMatrix(nodeMatrix, nodenumber)
	//	nodeMatrix.
		
		

	}

}