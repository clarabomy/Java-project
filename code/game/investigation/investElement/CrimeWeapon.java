/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.game.investigation.investElement;

/**
 *
 * @author Thibaut
 */
public class CrimeWeapon extends InvestElement {
    String m_weaponType;

    
    public CrimeWeapon(String weaponType, int[] refProof) {
        super(refProof);
        this.m_weaponType = weaponType;
    }

    
    public String getM_weaponType() {
        return m_weaponType;
    }
    
}
