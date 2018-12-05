package execption;

public class DataResponseException extends RuntimeException {

	/**
	 * 异步提交返回数据
	 */
	private static final long serialVersionUID = 1L;

	public DataResponseException(){}


	public DataResponseException(String msg){
		super(msg);
	}

}
