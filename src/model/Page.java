package model;

public class Page {

	int showPage;//Ҫ��ʾ��ҳ��
	
	int pageNum;//�ܵ�ҳ��
	
	int pageRecord;//ÿһҳ��ʾ�ļ�¼��

	public int getShowPage() {
		return showPage;
		
	}

	//���Ҫ��ʾ��ҳ�������������û�����ѡ�����ʾ��ҳ��
	public void setShowPage(String showPage1) {
		  if(showPage1==null||showPage1=="")//(����һ�η��ʵ�ʱ����ʾ���ǵ�һҳ)
		  {
			  showPage1="1";//��ҳ��Ϊ�ջ�����û�в���ʱ����ʾ��һ��ҳ��
		  }
		  showPage=Integer.parseInt(showPage1);	  
	}

	public int getPageNum() {
		return pageNum;
	}

	//����ܵ�ҳ����
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
	
	//���쳣����ʾҳ�����д���
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
