package Error;

public class PostConditionError extends ContractError {

	private static final long serialVersionUID = 1L;

	public PostConditionError(String string) {
		super("Postcondition failed :"+ string);
	}

}
