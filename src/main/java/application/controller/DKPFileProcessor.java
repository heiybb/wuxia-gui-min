package application.controller;

import application.model.BangPaiMember;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static application.controller.DKPFileSet.BRACE_PAT;
import static application.controller.DateProcessor.rangeDate;
import static java.util.stream.Collectors.toList;

/**
 * @author heiybb
 * handle the dkp file process
 * convert the game dkp file to readable data format
 */
class DKPFileProcessor {
    private Pattern timeStamp = Pattern.compile("\\d{4}/\\d{2}/\\d{2} \\d{2}:\\d{2}\\s*");

    /**
     * @param dkpFile the dkp file
     * @return ArrayList<BangPaiMember>
     */
    ArrayList<BangPaiMember> fileToRecordList(File dkpFile) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(dkpFile.getPath()));
        List<String> dkpFileLines = br.lines().collect(toList());
        br.close();
        ArrayList<ArrayList<String>> dkpEventListCurrentWeek = getDateRangeEvent(dkpFileLines);

        return eventListToMemberEvent(dkpEventListCurrentWeek);
    }

    private ArrayList<ArrayList<String>> getDateRangeEvent(List<String> eventList) {

        ArrayList<ArrayList<String>> rList = new ArrayList<>();
        AtomicInteger count = new AtomicInteger();

        rList.add(count.get(), new ArrayList<>());

        for (String recordLine : eventList) {
            if (!"".equals(recordLine)) {
                Matcher m = timeStamp.matcher(recordLine);

                if (m.find()) {
                    //check the validated week day
                    rangeDate.forEach(day -> {
                        if (recordLine.contains(day)) {
                            rList.get(count.get()).add(recordLine);
                        }
                    });
                } else {
                    //append the id directly
                    rList.get(count.get()).add(recordLine);
                }
            } else {
                rList.add(count.incrementAndGet(), new ArrayList<>());
            }
        }
        return rList;
    }

    /**
     * @param eventList the eventList contain the id and event
     * @return return an ArrayList of BangPaiMember type
     */
    private ArrayList<BangPaiMember> eventListToMemberEvent(ArrayList<ArrayList<String>> eventList) {
        ArrayList<BangPaiMember> memberArrayList = new ArrayList<>();
        eventList.forEach(eventArray -> {
            if (eventArray.size() != 0) {
                BangPaiMember tmpBangPaiMember = new BangPaiMember(eventArray.get(0));
                if (eventArray.size() > 1) {
                    //loop from 1 to bypass the id in the ArrayList
                    for (int i = 1; i < eventArray.size() - 1; i++) {
                        tmpBangPaiMember.getEvents().add(eventArray.get(i));
                        eventExtract(tmpBangPaiMember, eventArray.get(i));
                    }
                }
                memberArrayList.add(tmpBangPaiMember);
            }
        });
        memberArrayList.forEach(BangPaiMember::updateReward);
        return memberArrayList;

    }

    //提取中文括号后的数值 例：完成帮派委任（10次）的DKP为1
    private Integer braceExtract(String event) {
        int rtNum = 0;
        Matcher m = BRACE_PAT.matcher(event);
        if (m.find()) {
            rtNum = Integer.parseInt(m.group());
        }
        return rtNum;
    }

    private void eventExtract(BangPaiMember member, String event) {
        if (event.contains("帮派委任")) {
            member.setBpwr(member.getBpwr() + braceExtract(event));
        }
        if (event.contains("帮派醉侠")) {
            member.setBpzx(1);
        }
        if (event.contains("血战海河洲")) {
            member.setXzhh(1);
        }
        if (event.contains("帮派跨服战场")) {
            member.setKfzc(member.getKfzc() + 1);
        }
        if (event.contains("帮派玉石")) {
            member.setZijin(member.getYushi() + braceExtract(event));
        }
        if (event.contains("帮派资金")) {
            member.setYushi(member.getZijin() + braceExtract(event));
        }

        //联盟
        if (event.contains("争锋战")) {
            member.setZhenfeng(1);
        }
        if (event.contains("掠夺战")) {
            member.setLueduo(member.getLueduo() + 1);
        }
    }
}
