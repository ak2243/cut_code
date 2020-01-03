package cutcode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import logicalBlocks.Block;
import logicalBlocks.PrintBlock;
public class test {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException, ParseException {
		JSONObject ojsimpson = new JSONObject();
		List<Block> hiya = new ArrayList<Block>();
		for(int i = 0; i < 10; i++) {
			PrintBlock print = new PrintBlock();
			print.setPrint("print");
			hiya.add(print);
		}
		JSONArray list = new JSONArray();
		for(Block b : hiya) {
			list.add(b.toString());
		}
		ojsimpson.put("hiya", list);
		ojsimpson.put("id", 5);
		String json = ojsimpson.toJSONString();
		JSONParser parser = new JSONParser();
		JSONObject acquit = (JSONObject) parser.parse(json);
		JSONArray arr = (JSONArray) acquit.get("hiya");
		Iterator<String> iterator = arr.iterator();
		while(iterator.hasNext()) {
			System.out.print(iterator.next());
		}
	}
}