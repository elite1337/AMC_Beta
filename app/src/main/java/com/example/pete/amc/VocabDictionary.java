package com.example.pete.amc;

import io.realm.RealmObject;

public class VocabDictionary extends RealmObject {

    String vocab;
    String vocabPoS;
    String vocabChi;
    int vocabLv;
    int vocabPt;

    @Override
    public String toString() {
        return "Vocab{" +
                "vocab='" + vocab + '\'' +
                ", vocabPoS='" + vocabPoS + '\'' +
                ", vocabChi='" + vocabChi + '\'' +
                ", vocabLv=" + vocabLv +
                ", vocabPt=" + vocabPt +
                '}';
    }

    public int getVocabPt() {
        return vocabPt;
    }

    public void setVocabPt(int vocabPt) {
        this.vocabPt = vocabPt;
    }

    public String getVocab() {
        return vocab;
    }

    public void setVocab(String vocab) {
        this.vocab = vocab;
    }

    public String getVocabPoS() {
        return vocabPoS;
    }

    public void setVocabPoS(String vocabPoS) {
        this.vocabPoS = vocabPoS;
    }

    public String getVocabChi() {
        return vocabChi;
    }

    public void setVocabChi(String vocabChi) {
        this.vocabChi = vocabChi;
    }

    public int getVocabLv() {
        return vocabLv;
    }

    public void setVocabLv(int vocabLv) {
        this.vocabLv = vocabLv;
    }
}
