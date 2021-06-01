package camada.transporte;

import java.util.ArrayList;
import java.util.List;

public class Pacote {
	 
	private String ID;
	private int size;
	private List<Pair<String,Integer>> info_;
	
	
	class Pair<L,R> {
	    private L l;
	    private R r;
	    public Pair(L l, R r){
	        this.l = l;
	        this.r = r;
	    }
	    public L getL(){ return l; }
	    public R getR(){ return r; }
	    public void setL(L l){ this.l = l; }
	    public void setR(R r){ this.r = r; }
	}
	
	
	public Pacote(String i) {
		ID = i;
		size = 0;
		Pair<String,Integer> a;
		info_ = new ArrayList<Pair<String,Integer>>();
	}
	
	
	public void add_info(String Bytes) {
		Integer id_frame = Byte.toUnsignedInt(Byte.parseByte(Bytes.substring(24, 32),2));		
		if(Bytes.charAt(17) == '1') size = id_frame;		
		
		
		
	}
	
	
	
	
}
