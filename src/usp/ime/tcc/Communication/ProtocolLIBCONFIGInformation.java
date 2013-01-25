package usp.ime.tcc.Communication;

import java.io.Serializable;

public class ProtocolLIBCONFIGInformation extends ProtocolInformation implements Serializable{

	private static final long serialVersionUID = 1L;

	private int raio;
	private int angulo;
	
	public ProtocolLIBCONFIGInformation(AppProtocol app, int raio, int angulo) {
		super(app);
		this.raio = raio;
		this.angulo = angulo;
	}

	public int getRaio() {
		return raio;
	}

	public void setRaio(int raio) {
		this.raio = raio;
	}

	public int getAngulo() {
		return angulo;
	}

	public void setAngulo(int angulo) {
		this.angulo = angulo;
	}

}
