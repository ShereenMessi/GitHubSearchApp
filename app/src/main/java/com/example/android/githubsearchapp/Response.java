package com.example.android.githubsearchapp;

import java.util.List;

public class Response{
	private int totalCount;
	private boolean incompleteResults;
	private List<ItemsItem> items;

	public void setTotalCount(int totalCount){
		this.totalCount = totalCount;
	}

	public int getTotalCount(){
		return totalCount;
	}

	public void setIncompleteResults(boolean incompleteResults){
		this.incompleteResults = incompleteResults;
	}

	public boolean isIncompleteResults(){
		return incompleteResults;
	}

	public void setItems(List<ItemsItem> items){
		this.items = items;
	}

	public List<ItemsItem> getItems(){
		return items;
	}

	@Override
 	public String toString(){
		return 
			"Response{" + 
			"total_count = '" + totalCount + '\'' + 
			",incomplete_results = '" + incompleteResults + '\'' + 
			",items = '" + items + '\'' + 
			"}";
		}
}