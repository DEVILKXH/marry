package com.marry.inner.vo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.marry.inner.base.annotation.ExcelColumn;
import com.marry.inner.base.annotation.ExcelTable;

@ExcelTable(name = "第二届诏安县荔枝网销大赛报名表")
@Table(name = "TEAMS_ALL")
public class TeamsAllVo{

	private String id;
	
	@ExcelColumn(name = "团队名", index = 1)
	private String teamName;

	@ExcelColumn(name = "日期", index = 2)
    private String teamData;

	@ExcelColumn(name = "联系人", index = 3)
    private String teamPerson;

	@ExcelColumn(name = "联系方式", index = 4)
    private String teamTel;

    private String teamAddr;

    private Integer teamNum;

    private String teamRemark;
	
	@Column(name = "TEAM_LOGO")
	private String TeamLogo;
	
	@Column(name = "CREATE_TIME")
    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName == null ? null : teamName.trim();
    }

    public String getTeamData() {
        return teamData;
    }

    public void setTeamData(String teamData) {
        this.teamData = teamData == null ? null : teamData.trim();
    }

    public String getTeamPerson() {
        return teamPerson;
    }

    public void setTeamPerson(String teamPerson) {
        this.teamPerson = teamPerson == null ? null : teamPerson.trim();
    }

    public String getTeamTel() {
        return teamTel;
    }

    public void setTeamTel(String teamTel) {
        this.teamTel = teamTel == null ? null : teamTel.trim();
    }

    public String getTeamAddr() {
        return teamAddr;
    }

    public void setTeamAddr(String teamAddr) {
        this.teamAddr = teamAddr == null ? null : teamAddr.trim();
    }

    public Integer getTeamNum() {
        return teamNum;
    }

    public void setTeamNum(Integer teamNum) {
        this.teamNum = teamNum;
    }

    public String getTeamRemark() {
        return teamRemark;
    }

    public void setTeamRemark(String teamRemark) {
        this.teamRemark = teamRemark == null ? null : teamRemark.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public String getTeamLogo() {
		return TeamLogo;
	}

	public void setTeamLogo(String teamLogo) {
		TeamLogo = teamLogo;
	}
}