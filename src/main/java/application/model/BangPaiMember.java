package application.model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;


public class BangPaiMember extends RecursiveTreeObject<BangPaiMember> {

    /**
     * 帮派成员ID
     */
    private StringProperty id;

    /**
     * 帮派委任
     */
    private IntegerProperty bpwr;

    /**
     * 帮派醉侠
     */
    private IntegerProperty bpzx;

    /**
     * 血战海河州
     */
    private IntegerProperty xzhh;

    /**
     * 跨服战场
     */
    private IntegerProperty kfzc;

    /**
     * 联盟掠夺
     */
    private IntegerProperty lueduo;

    /**
     * 争锋
     */
    private IntegerProperty zhenfeng;

    /**
     * 资金捐献
     */
    private IntegerProperty zijin;

    /**
     * 玉石捐献
     */
    private IntegerProperty yushi;

    /**
     * DKP计算
     */
    private IntegerProperty dkp;

    /**
     * 箱子发放
     */
    private IntegerProperty reward;

    /**
     * 帮派成员dkp事件
     */
    private ArrayList<String> events = new ArrayList<>();

    public BangPaiMember(String id) {
        this.id = new SimpleStringProperty(id);
        this.bpwr = new SimpleIntegerProperty(0);
        this.bpzx = new SimpleIntegerProperty(0);
        this.xzhh = new SimpleIntegerProperty(0);
        this.kfzc = new SimpleIntegerProperty(0);
        this.lueduo = new SimpleIntegerProperty(0);
        this.zhenfeng = new SimpleIntegerProperty(0);
        this.zijin = new SimpleIntegerProperty(0);
        this.yushi = new SimpleIntegerProperty(0);
        this.dkp = new SimpleIntegerProperty(0);
        this.reward = new SimpleIntegerProperty(0);
    }

    public BangPaiMember() {
        //empty constructor
    }

    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public StringProperty idProperty() {
        return id;
    }

    public int getBpwr() {
        return bpwr.get();
    }

    public void setBpwr(int bpwr) {
        this.bpwr.set(bpwr);
        updateReward();
    }

    public IntegerProperty bpwrProperty() {
        return bpwr;
    }

    public int getBpzx() {
        return bpzx.get();
    }

    public void setBpzx(int bpzx) {
        this.bpzx.set(bpzx);
    }

    public IntegerProperty bpzxProperty() {
        return bpzx;
    }

    public int getXzhh() {
        return xzhh.get();
    }

    public void setXzhh(int xzhh) {
        this.xzhh.set(xzhh);
    }

    public IntegerProperty xzhhProperty() {
        return xzhh;
    }

    public int getKfzc() {
        return kfzc.get();
    }

    public void setKfzc(int kfzc) {
        this.kfzc.set(kfzc);
    }

    public IntegerProperty kfzcProperty() {
        return kfzc;
    }

    public int getLueduo() {
        return lueduo.get();
    }

    public void setLueduo(int lueduo) {
        this.lueduo.set(lueduo);
    }

    public IntegerProperty lueduoProperty() {
        return lueduo;
    }

    public int getZhenfeng() {
        return zhenfeng.get();
    }

    public void setZhenfeng(int zhenfeng) {
        this.zhenfeng.set(zhenfeng);
    }

    public IntegerProperty zhenfengProperty() {
        return zhenfeng;
    }

    public int getZijin() {
        return zijin.get();
    }

    public void setZijin(int zijin) {
        this.zijin.set(zijin);
    }

    public IntegerProperty zijinProperty() {
        return zijin;
    }

    public int getYushi() {
        return yushi.get();
    }

    public void setYushi(int yushi) {
        this.yushi.set(yushi);
    }

    public IntegerProperty yushiProperty() {
        return yushi;
    }

    public int getDkp() {
        return dkp.get();
    }

    public void setDkp(int dkp) {
        this.dkp.set(dkp);
    }

    public IntegerProperty dkpProperty() {
        return dkp;
    }

    public int getReward() {
        return reward.get();
    }

    public void setReward(int reward) {
        this.reward.set(reward);
    }

    public IntegerProperty rewardProperty() {
        return reward;
    }

    public ArrayList<String> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<String> events) {
        this.events = events;
    }

    @Override
    public String toString() {
        String asString = this.getId() + "\t" +
                this.getBpwr() + "\t" +
                this.getBpzx() + "\t" +
                this.getXzhh() + "\t" +
                this.getBpzx() + "\t" +
                this.getLueduo() + "\t" +
                this.getZhenfeng() + "\t" +
                this.getZijin() + "\t" +
                this.getYushi() + "\t" +
                this.getDkp() + "\t" +
                this.getReward() + "\n";
        return asString;
    }

    public void updateReward() {
        int defaultRW = 0;
        //委任
        if (this.getBpwr() >= 210) {
            defaultRW += 3;
        } else {
            defaultRW += (this.getBpwr() % 7);
        }
        //醉侠 血战海河州
        if (this.getBpzx() == 1 && this.getXzhh() == 1) {
            defaultRW += 1;
        }
        //帮派跨服战场
        defaultRW += this.getKfzc();

        //争锋战
        defaultRW += this.getZhenfeng();

        //reduce waste
        if (defaultRW >= 8) {
            this.setReward(8);
        } else {
            this.setReward(defaultRW);
        }

        this.reward.set(defaultRW);
    }
}
