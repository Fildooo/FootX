package com.example.footx.DAO;


public class Response {

    private String m_pseudo ;
    private String m_mdp ;
    private String m_teamid;
    private  int m_id;

    public Response(String p, String m, String tid, int i){
        setM_pseudo(p);
        setM_mdp(m);
        setM_teamid(tid);
        setM_id(i);
    }

    public String getM_pseudo() {
        return m_pseudo;
    }

    public void setM_pseudo(String m_pseudo) {
        this.m_pseudo = m_pseudo;
    }

    public String getM_mdp() {
        return m_mdp;
    }

    public void setM_mdp(String m_mdp) {
        this.m_mdp = m_mdp;
    }

    public int getM_id() {
        return m_id;
    }

    public void setM_id(int m_id) {
        this.m_id = m_id;
    }

    public String getM_teamid() {
        return m_teamid;
    }

    public void setM_teamid(String m_teamid) {
        this.m_teamid = m_teamid;
    }
}
