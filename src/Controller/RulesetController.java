//
//
//  @ Project : JNU_IDS
//  @ Date : 2016-05-11
//  @ Author : 채두걸, 김민진, 김연수, 정찬우, 최민정
//
//


package Controller;

public class RulesetController extends FileController {
	private String ruleset;

	//프로그램에 쓸 룰셋 설정
	public void setRuleset() {
		
		
	}

	//현재 룰셋 상태 반환
	public String getNowRuleset() {
		
		ruleset = "ip";
		
		return ruleset;

	}
}
