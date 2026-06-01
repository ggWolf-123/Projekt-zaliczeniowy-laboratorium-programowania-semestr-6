package com.si;

public class MathRequest {
    private byte[] imageMath;
    private String sentenseToCheckMath;

    public MathRequest() {
    }
    public byte[] getImageMath() {
        return imageMath;
    }

    public void setImageMath(byte[] imageMath) {
        this.imageMath = imageMath;
    }

    public MathRequest(String sentenseToCheckMath, byte[] imageMath) {
        this.sentenseToCheckMath = sentenseToCheckMath;
        this.imageMath = imageMath;
    }
    public String getSenteceToCheckMath() {
        return sentenseToCheckMath;
    }

    public void setSentenceToCheckMath(String sentenseToCheckMath) {
        this.sentenseToCheckMath = sentenseToCheckMath;
    }

}
