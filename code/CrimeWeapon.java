/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

/**
 *
 * @author ISEN
 */
public class CrimeWeapon extends InvestElement {
    String weaponType;

    public CrimeWeapon(String weaponType, int[] refProof) {
        super(refProof);
        this.weaponType = weaponType;
    }

    public String getWeaponType() {
        return weaponType;
    }
    
}
