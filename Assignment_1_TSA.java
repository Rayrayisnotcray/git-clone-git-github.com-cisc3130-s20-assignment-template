//Raymond Xu
//23396897

package Assignment;

import java.io.*;

public class Assignment_1_TSA {
	
	public static void main(String[] args) throws IOException {
		
		int rows = 20, cols = 2,artistNum = 0, count = 0;
		String[][] myList = new String[rows][cols];	
		
		TopStreamingArtists TSA = new TopStreamingArtists(); 
	
		String fileName = "regional-global-weekly-2020-01-17--2020-01-24.csv";
		
		try(BufferedReader br = new BufferedReader(new FileReader(new File(fileName)))){
			System.out.println(br.readLine()); //ignores first line 
			System.out.println(br.readLine()); //ignores second line
			String data = "";
			while((data = br.readLine()) != null) {		
				String [] Node = data.split(",");
				String temp = Node[2];
				
				System.out.println(temp);
				
				if(Character.isLetter(temp.charAt(0)) == false)
					temp = temp.substring(1, temp.length()-1);	
							
					int result = search(myList, temp, artistNum);
					
					if(result != -1) {
						String s = myList[result][1];
						count = Integer.parseInt(s) + 1;
						System.out.println(count);
						myList[result][1] = String.valueOf(count);					
					}
					else {
						myList[artistNum][0] = temp;
						myList[artistNum][1] = "1";
						artistNum++;
						if(artistNum == myList.length)
							myList = expand(myList);
					}					
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(print(myList, artistNum));
		
		for(int i = 0; i < artistNum; i++) {
			TSA.addArtist(myList[i][0]);
		}
		
		TSA.displayList();
	}
	
	public static String[][] expand(String[][] prev) {
		String[][] newArray = new String[prev.length+20][2];
		for(int i = 0; i < prev.length;i++) {
			newArray[i][0] = prev[i][0];
			newArray[i][1] = prev[i][1];
		}
		return newArray;
	}
	
	public static String print(String[][] myList, int artistNum) {
		String result ="";
		for(int i = 0; i < artistNum; i++)
			result += myList[i][0] + " " +myList[i][1] + " time(s)"+ "\n";
		return result;
	}
	
	public static int search(String[][] array, String value, int num) {
		for(int i = 0; i < num ; i++) {
			String temp = array[i][0];
			if(temp.equals(value))
				return i;
		}
		return -1;
	}
	
	public static class TopStreamingArtists{
		private Artist first;
		public TopStreamingArtists(){
			first = null;
		}
		
		public boolean isEmpty() {
			
			return(first==null);
			
		}
		
		public void addArtist(String n) {
			Artist current;
			Artist newArtist = new Artist(n);
			if(first==null || first.name.compareToIgnoreCase(n) >= 0) {
				
				newArtist.next = first;
				first = newArtist;
			}
			else {
				current = first;
				
				while(current.next != null && current.next.name.compareToIgnoreCase(n) < 0) 
					current = current.next;
				newArtist.next = current.next;
				current.next = newArtist;
			}
		}
		
		public void displayList() {
			Artist temp = first;
			
			while(temp != null) {
				System.out.println(temp.name);
				temp = temp.next;
			}
		}
	}
	
	public static class Artist{
		private String name;
		private Artist next;
		
		Artist() {
			name = "";
			next = null;
		}
		
		Artist(String n){
			name =n;
			next = null;
		}
		
		Artist(String n, Artist ne){
			name = n;
			next = ne;
		}
	}
}
