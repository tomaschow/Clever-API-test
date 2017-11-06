import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public class RootElement {
	JsonArray data;
	JsonElement paging;
	JsonArray links;
	public JsonArray getData() {
		return data;
	}
	public void setDatas(JsonArray data) {
		this.data = data;
	}
	public JsonElement getPaging() {
		return paging;
	}
	public void setPaging(JsonElement paging) {
		this.paging = paging;
	}
	public JsonArray getLinks() {
		return links;
	}
	public void setLinks(JsonArray links) {
		this.links = links;
	}
	
}
