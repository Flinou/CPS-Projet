package Services;

public interface PersonnageJouableService extends PersonnageService {
	
	public void init(int i, int j, String name);
	public int getForceVitale();
	public String getName();

}
