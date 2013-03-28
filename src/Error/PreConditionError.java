package Error;

public class PreConditionError extends ContractError {

	private static final long serialVersionUID = 1L;

	public PreConditionError(String string) {
		super("Precondition failed : "+string);
	}

}
