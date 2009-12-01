/*
Copyright (C) 2008-2009  Santiago Carot Nemesio
email: scarot@libresoft.es

This program is a (FLOS) free libre and open source implementation
of a multiplatform manager device written in java according to the
ISO/IEEE 11073-20601. Manager application is designed to work in 
DalvikVM over android platform.

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.

*/

package ieee_11073.part_20601.asn1;
//
// This file was generated by the BinaryNotes compiler.
// See http://bnotes.sourceforge.net 
// Any modifications to this file will be lost upon recompilation of the source ASN.1. 
//

import ieee_11073.part_20601.phd.channel.ApduChannelType;

import org.bn.*;
import org.bn.annotations.*;
import org.bn.annotations.constraints.*;
import org.bn.coders.*;
import org.bn.types.*;




    @ASN1PreparedElement
    @ASN1Choice ( name = "ApduType" )
    public class ApduType extends ApduChannelType {
            
        @ASN1Element ( name = "aarq", isOptional =  false , hasTag =  true, tag = 57856 , hasDefaultValue =  false, hasExplicitOrder = true, declarationOrder = 0  )
    
	private AarqApdu aarq = null;
                
  
        @ASN1Element ( name = "aare", isOptional =  false , hasTag =  true, tag = 58112 , hasDefaultValue =  false, hasExplicitOrder = true, declarationOrder = 1  )
    
	private AareApdu aare = null;
                
  
        @ASN1Element ( name = "rlrq", isOptional =  false , hasTag =  true, tag = 58368 , hasDefaultValue =  false, hasExplicitOrder = true, declarationOrder = 2  )
    
	private RlrqApdu rlrq = null;
                
  
        @ASN1Element ( name = "rlre", isOptional =  false , hasTag =  true, tag = 58624 , hasDefaultValue =  false, hasExplicitOrder = true, declarationOrder = 3  )
    
	private RlreApdu rlre = null;
                
  
        @ASN1Element ( name = "abrt", isOptional =  false , hasTag =  true, tag = 58880 , hasDefaultValue =  false, hasExplicitOrder = true, declarationOrder = 4  )
    
	private AbrtApdu abrt = null;
                
  
        @ASN1Element ( name = "prst", isOptional =  false , hasTag =  true, tag = 59136 , hasDefaultValue =  false, hasExplicitOrder = true, declarationOrder = 5  )
    
	private PrstApdu prst = null;
                
  
        
        public AarqApdu getAarq () {
            return this.aarq;
        }

        public boolean isAarqSelected () {
            return this.aarq != null;
        }

        private void setAarq (AarqApdu value) {
            this.aarq = value;
        }

        
        public void selectAarq (AarqApdu value) {
            this.aarq = value;
            
                    setAare(null);
                
                    setRlrq(null);
                
                    setRlre(null);
                
                    setAbrt(null);
                
                    setPrst(null);
                            
        }

        
  
        
        public AareApdu getAare () {
            return this.aare;
        }

        public boolean isAareSelected () {
            return this.aare != null;
        }

        private void setAare (AareApdu value) {
            this.aare = value;
        }

        
        public void selectAare (AareApdu value) {
            this.aare = value;
            
                    setAarq(null);
                
                    setRlrq(null);
                
                    setRlre(null);
                
                    setAbrt(null);
                
                    setPrst(null);
                            
        }

        
  
        
        public RlrqApdu getRlrq () {
            return this.rlrq;
        }

        public boolean isRlrqSelected () {
            return this.rlrq != null;
        }

        private void setRlrq (RlrqApdu value) {
            this.rlrq = value;
        }

        
        public void selectRlrq (RlrqApdu value) {
            this.rlrq = value;
            
                    setAarq(null);
                
                    setAare(null);
                
                    setRlre(null);
                
                    setAbrt(null);
                
                    setPrst(null);
                            
        }

        
  
        
        public RlreApdu getRlre () {
            return this.rlre;
        }

        public boolean isRlreSelected () {
            return this.rlre != null;
        }

        private void setRlre (RlreApdu value) {
            this.rlre = value;
        }

        
        public void selectRlre (RlreApdu value) {
            this.rlre = value;
            
                    setAarq(null);
                
                    setAare(null);
                
                    setRlrq(null);
                
                    setAbrt(null);
                
                    setPrst(null);
                            
        }

        
  
        
        public AbrtApdu getAbrt () {
            return this.abrt;
        }

        public boolean isAbrtSelected () {
            return this.abrt != null;
        }

        private void setAbrt (AbrtApdu value) {
            this.abrt = value;
        }

        
        public void selectAbrt (AbrtApdu value) {
            this.abrt = value;
            
                    setAarq(null);
                
                    setAare(null);
                
                    setRlrq(null);
                
                    setRlre(null);
                
                    setPrst(null);
                            
        }

        
  
        
        public PrstApdu getPrst () {
            return this.prst;
        }

        public boolean isPrstSelected () {
            return this.prst != null;
        }

        private void setPrst (PrstApdu value) {
            this.prst = value;
        }

        
        public void selectPrst (PrstApdu value) {
            this.prst = value;
            
                    setAarq(null);
                
                    setAare(null);
                
                    setRlrq(null);
                
                    setRlre(null);
                
                    setAbrt(null);
                            
        }

        
  

	    public void initWithDefaults() {
	    }

        private static IASN1PreparedElementData preparedData = CoderFactory.getInstance().newPreparedElementData(ApduType.class);
        public IASN1PreparedElementData getPreparedData() {
            return preparedData;
        }


    }
            