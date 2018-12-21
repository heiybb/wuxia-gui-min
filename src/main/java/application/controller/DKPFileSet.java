package application.controller;

import java.util.regex.Pattern;

public interface DKPFileSet {
    String DKPFILE = "BangPai_DKPModifyRecord.txt";
    String JILIFILE = "BangPai_DKPFaFangJiLi.txt";
    Pattern BRACE_PAT = Pattern.compile("(?<=（)(.+?)(?=[\u4e00-\u9fa5]）)");
    String JiLiTemplate ="发放激励\n领取情况\t帮众\t等级\t职位\t剩余PVP-DKP\t修改PVP-DKP\t剩余PVE-DKP\t修改PVE-DKP\t发放数量\n";
}
