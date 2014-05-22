package model;

public class Page {

	int showPage;//要显示的页数
	
	int pageNum;//总的页数
	
	int pageRecord;//每一页显示的记录数

	public int getShowPage() {
		return showPage;
		
	}

	//获得要显示的页数，参数是由用户进行选择的显示的页数
	public void setShowPage(String showPage1) {
		  if(showPage1==null||showPage1=="")//(当第一次访问的时候，显示的是第一页)
		  {
			  showPage1="1";//当页码为空或者是没有参数时，显示第一个页面
		  }
		  showPage=Integer.parseInt(showPage1);	  
	}

	public int getPageNum() {
		return pageNum;
	}

	//获得总的页面数
	public void setPageNum(long sumRecord) {
		if(sumRecord%pageRecord==0) {
			  pageNum=(int) (sumRecord/pageRecord);
		  }
		  else {
			  pageNum=(int) (sumRecord/pageRecord+1);
		  }
	}

	public int getPageRecord() {
		return pageRecord;
	}

	public void setPageRecord(int pageRecordAmount) {
		this.pageRecord = pageRecordAmount;
	}
	
	//对异常的显示页数进行处理
	public void showPageCheck() {
		if(showPage>=pageNum)
	      {
	    	  showPage=pageNum;
	      }
		  if(showPage<1)
		  {
			  showPage=1;
		  }
	}
}
