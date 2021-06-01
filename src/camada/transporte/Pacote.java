package camada.transporte;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Pacote {
	 
	private String ID;
	private int size;
	private List<Pair<String,Integer>> info_;
	private Set<Integer> info_id;
	
	
	
	
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
		size = -1;
		info_ = new ArrayList<Pair<String,Integer>>();		
		info_id = new HashSet<Integer>();
	
	}
	
	
	public void add_info(String Bytes) {
		
		

		Integer id_frame = Byte.toUnsignedInt(Byte.parseByte(Bytes.substring(24, 32),2));
		if(info_id.contains(id_frame)) return;
		
		if(Bytes.charAt(17) == '1') size = id_frame + 1;		
		info_id.add(id_frame);
		info_.add(new Pair<String,Integer>(Bytes.substring(0,18),id_frame));
		

	}
	
	
	
	public String get_pacote() {
		info_.sort(Comparator.comparing(p -> p.getR()));
		String r ="";		
		for (int i = 0; i< info_.size(); i++)
			r = r + info_.get(i).getL();
		return r;
	}
	
	
	public String get_id() {
		return ID;
	}
	
	
	public boolean done() {
		return info_.size() == size;
	}
	
	
	
}
