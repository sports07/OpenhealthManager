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
package ieee_11073.part_10101;

	/*************************************************************************************************
	 * This interface represents nomenclature codes
	 * This annex contains the nomenclature codes used in this standard. They are either copied from
	 * ISO/IEEE 11073-10101 or created for this standard and incorporated into ISO/IEEE 11073-10101.
	 * The format used here follows the one defined in ISO/IEEE 11073-10101.
	 *************************************************************************************************/
public interface Nomenclature {

	/* Partition codes																				 		 */
	public static final int MDC_PART_OBJ						=	1;		/* Object Infrastr.              */
	public static final int MDC_PART_SCADA						=	2;		/* SCADA (Physio IDs             */
	public static final int MDC_PART_DIM						=	4;		/* Dimension                     */
	public static final int MDC_PART_INFRA						=	8;		/* Infrastructure                */
	public static final int MDC_PART_PHD_DM						=	128;	/* Disease Mgmt                  */
	public static final int MDC_PART_PHD_HF						=	129;	/* Health and Fitness            */
	public static final int MDC_PART_PHD_AI						=	130;	/* Aging Independently           */
	public static final int MDC_PART_RET_CODE					=	255;	/* Return Codes                  */
	public static final int MDC_PART_EXT_NOM					=	256;	/* Ext. Nomenclature             */

	/*************************************************************************************************
	* From Object Infrastructure (MDC_PART_OBJ)
	**************************************************************************************************/
	public static final int MDC_MOC_VMO_METRIC					=	4;		/*                               */
	public static final int MDC_MOC_VMO_METRIC_ENUM				=	5;		/*                               */
	public static final int MDC_MOC_VMO_METRIC_NU				=	6;		/*                               */
	public static final int MDC_MOC_VMO_METRIC_SA_RT			=	9; 		/*                               */
	public static final int MDC_MOC_SCAN						=	16;		/*                               */
	public static final int MDC_MOC_SCAN_CFG					=	17;		/*                               */
	public static final int MDC_MOC_SCAN_CFG_EPI				=	18;		/*                               */
	public static final int MDC_MOC_SCAN_CFG_PERI				=	19;		/*                               */
	public static final int MDC_MOC_VMS_MDS_SIMP				=	37;		/*                               */
	public static final int MDC_MOC_VMO_PMSTORE					=	61;		/*                               */
	public static final int MDC_MOC_PM_SEGMENT					=	62;		/*                               */
	public static final int MDC_ATTR_AL_OP_STAT					=	2310;	/*                               */
	public static final int MDC_ATTR_CONFIRM_MODE				=	2323;	/*                               */
	public static final int MDC_ATTR_CONFIRM_TIMEOUT			=	2324;	/*                               */
	public static final int MDC_ATTR_ID_HANDLE					=	2337;	/*                               */
    public static final int MDC_ATTR_ID_INSTNO 					=	2338;	/*                               */
    public static final int MDC_ATTR_ID_LABEL_STRING			=	2343;	/*                               */
    public static final int MDC_ATTR_ID_MODEL					=	2344;	/*                               */
    public static final int MDC_ATTR_ID_PHYSIO					=	2347;	/*                               */
    public static final int MDC_ATTR_ID_PROD_SPECN				=	2349;	/*                               */
    public static final int MDC_ATTR_ID_TYPE					=	2351;	/*                               */
    public static final int MDC_ATTR_LIMIT_CURR					=	2356;	/*                               */
    public static final int MDC_ATTR_METRIC_STORE_CAPAC_CNT		=	2369;	/*                               */
    public static final int MDC_ATTR_METRIC_STORE_SAMPLE_ALG 	=	2371;	/*                               */
    public static final int MDC_ATTR_METRIC_STORE_USAGE_CNT 	=	2372;	/*                               */
    public static final int MDC_ATTR_MSMT_STAT					=	2375;	/*                               */
    public static final int MDC_ATTR_NU_ACCUR_MSMT				=	2378;	/*                               */
    public static final int MDC_ATTR_NU_CMPD_VAL_OBS			=	2379;	/*                               */
    public static final int MDC_ATTR_NU_VAL_OBS					=	2384;	/*                               */
    public static final int MDC_ATTR_NUM_SEG					=	2385;	/*                               */
    public static final int MDC_ATTR_OP_STAT					=	2387;	/*                               */
    public static final int MDC_ATTR_POWER_STAT					=	2389;	/*                               */
	public static final int MDC_ATTR_SA_SPECN					=	2413;	/*                               */
	public static final int MDC_ATTR_SCALE_SPECN_I16			=	2415;	/*                               */
	public static final int MDC_ATTR_SCALE_SPECN_I32			=	2416;	/*                               */
	public static final int MDC_ATTR_SCALE_SPECN_I8				=   2417;	/*                               */
	public static final int MDC_ATTR_SCAN_REP_PD				=   2421;	/*                               */
	public static final int MDC_ATTR_SEG_USAGE_CNT				=   2427;	/*                               */
	public static final int MDC_ATTR_SYS_ID						=   2436;	/*                               */
	public static final int MDC_ATTR_SYS_TYPE					=   2438;	/*                               */
	public static final int MDC_ATTR_TIME_ABS					=   2439;	/*                               */
	public static final int MDC_ATTR_TIME_BATT_REMAIN			=	2440;	/*                               */
	public static final int MDC_ATTR_TIME_END_SEG				=   2442;	/*                               */
	public static final int MDC_ATTR_TIME_PD_SAMP				=   2445;	/*                               */
	public static final int MDC_ATTR_TIME_REL					=   2447;	/*                               */
	public static final int MDC_ATTR_TIME_STAMP_ABS				=   2448;	/*                               */
	public static final int MDC_ATTR_TIME_STAMP_REL				=   2449;	/*                               */
	public static final int MDC_ATTR_TIME_START_SEG				=   2450;	/*                               */
	public static final int MDC_ATTR_TX_WIND					=   2453;	/*                               */
	public static final int MDC_ATTR_UNIT_CODE					=   2454;	/*                               */
	public static final int MDC_ATTR_UNIT_LABEL_STRING			=	2457;	/*                               */
	public static final int MDC_ATTR_VAL_BATT_CHARGE			=	2460;	/*                               */
	public static final int MDC_ATTR_VAL_ENUM_OBS				=   2462;	/*                               */
	public static final int MDC_ATTR_AL_COND					=	2476;	/*                               */
	public static final int MDC_ATTR_AL_OP_TEXT_STRING			=	2478;	/*                               */
	public static final int MDC_ATTR_TIME_REL_HI_RES			=   2536;	/*                               */
	public static final int MDC_ATTR_TIME_STAMP_REL_HI_RES		=	2537;	/*                               */
	public static final int MDC_ATTR_DEV_CONFIG_ID				=   2628;	/*                               */
	public static final int MDC_ATTR_MDS_TIME_INFO				=   2629;	/*                               */
	public static final int MDC_ATTR_METRIC_SPEC_SMALL			=	2630;	/*                               */
	public static final int MDC_ATTR_SOURCE_HANDLE_REF			=	2631;	/*                               */
	public static final int MDC_ATTR_SIMP_SA_OBS_VAL			=	2632;	/*                               */
	public static final int MDC_ATTR_ENUM_OBS_VAL_SIMP_OID		=	2633;	/*                               */
	public static final int MDC_ATTR_ENUM_OBS_VAL_SIMP_STR		=	2634;	/*                               */
	public static final int MDC_ATTR_REG_CERT_DATA_LIST			=	2635;	/*                               */
	public static final int MDC_ATTR_NU_VAL_OBS_BASIC			=	2636;	/*                               */
	public static final int MDC_ATTR_PM_STORE_CAPAB				=   2637;	/*                               */
	public static final int MDC_ATTR_PM_SEG_MAP					=   2638;	/*                               */
	public static final int MDC_ATTR_PM_SEG_PERSON_ID 			=	2639;	/*                               */
	public static final int MDC_ATTR_SEG_STATS					=   2640;	/*                               */
	public static final int MDC_ATTR_SEG_FIXED_DATA				=   2641;	/*                               */
	public static final int MDC_ATTR_PM_SEG_ELEM_STAT_ATTR		=	2642;	/*                               */
	public static final int MDC_ATTR_SCAN_HANDLE_ATTR_VAL_MAP	=	2643;	/*                               */
	public static final int MDC_ATTR_SCAN_REP_PD_MIN         	=	2644;	/*                               */
	public static final int MDC_ATTR_ATTRIBUTE_VAL_MAP       	=	2645;	/*                               */
	public static final int MDC_ATTR_NU_VAL_OBS_SIMP         	=	2646;	/*                               */
	public static final int MDC_ATTR_PM_STORE_LABEL_STRING 		=	2647;	/*                               */
	public static final int MDC_ATTR_PM_SEG_LABEL_STRING     	=	2648;	/*                               */
	public static final int MDC_ATTR_TIME_PD_MSMT_ACTIVE     	=	2649;	/*                               */
	public static final int MDC_ATTR_SYS_TYPE_SPEC_LIST      	=	2650;	/*                               */
	public static final int MDC_ATTR_METRIC_ID_PART				=   2655;	/*                               */
	public static final int MDC_ATTR_ENUM_OBS_VAL_PART       	=	2656;	/*                               */
	public static final int MDC_ATTR_SUPPLEMENTAL_TYPES      	=	2657;	/*                               */
	public static final int MDC_ATTR_TIME_ABS_ADJUST        	=	2658;	/*                               */
	public static final int MDC_ATTR_CLEAR_TIMEOUT				=   2659;	/*                               */
	public static final int MDC_ATTR_TRANSFER_TIMEOUT        	=	2660;	/*                               */
	public static final int MDC_ATTR_ENUM_OBS_VAL_SIMP_BIT_STR  =	2661;	/*                               */
	public static final int MDC_ATTR_ENUM_OBS_VAL_BASIC_BIT_STR =	2662;	/*                               */
	public static final int MDC_ATTR_METRIC_STRUCT_SMALL		=   2675;	/*                               */
	public static final int MDC_ATTR_NU_CMPD_VAL_OBS_SIMP  		=	2676;	/*                               */
	public static final int MDC_ATTR_NU_CMPD_VAL_OBS_BASIC 		=	2677;	/*                               */
	public static final int MDC_ATTR_ID_PHYSIO_LIST				=	2678;	/*                               */
	public static final int MDC_ATTR_SCAN_HANDLE_LIST 			=	2679;	/*                               */
	/* Partition: ACT */
	public static final int MDC_ACT_SEG_CLR 					=	3084;	/*                               */
	public static final int MDC_ACT_SEG_GET_INFO 				=	3085;	/*                               */
	public static final int MDC_ACT_SET_TIME 					=	3095;	/*                               */
	public static final int MDC_ACT_DATA_REQUEST 				=	3099;	/*                               */
	public static final int MDC_ACT_SEG_TRIG_XFER 				=	3100;	/*                               */
	public static final int MDC_NOTI_CONFIG 					=	3356;	/*                               */
	public static final int MDC_NOTI_SCAN_REPORT_FIXED       	=	3357;	/*                               */
	public static final int MDC_NOTI_SCAN_REPORT_VAR         		=	3358;	/*                           */
	public static final int MDC_NOTI_SCAN_REPORT_MP_FIXED    		=	3359;	/*                           */
	public static final int MDC_NOTI_SCAN_REPORT_MP_VAR      		=	3360;	/*                           */
	public static final int MDC_NOTI_SEGMENT_DATA 					=	3361;	/*                           */
	public static final int MDC_NOTI_UNBUF_SCAN_REPORT_VAR 			=	3362;	/*                           */
	public static final int MDC_NOTI_UNBUF_SCAN_REPORT_FIXED		=	3363;	/*                           */
	public static final int MDC_NOTI_UNBUF_SCAN_REPORT_GROUPED		=	3364;	/*                           */
	public static final int MDC_NOTI_UNBUF_SCAN_REPORT_MP_VAR		=	3365;	/*                           */
	public static final int MDC_NOTI_UNBUF_SCAN_REPORT_MP_FIXED		=	3366;	/*                           */
	public static final int MDC_NOTI_UNBUF_SCAN_REPORT_MP_GROUPED 	=	3367;	/*                           */
	public static final int MDC_NOTI_BUF_SCAN_REPORT_VAR			=	3368;	/*                           */
	public static final int MDC_NOTI_BUF_SCAN_REPORT_FIXED			=	3369;	/*                           */
	public static final int MDC_NOTI_BUF_SCAN_REPORT_GROUPED   		=	3370;	/*                           */
	public static final int MDC_NOTI_BUF_SCAN_REPORT_MP_VAR			=	3371;	/*                           */
	public static final int MDC_NOTI_BUF_SCAN_REPORT_MP_FIXED		=	3372;	/*                           */
	public static final int MDC_NOTI_BUF_SCAN_REPORT_MP_GROUPED		=	3373;	/*                           */

	/*************************************************************************************************
	* From Medical supervisory control and data acquisition (MDC_PART_SCADA)
	**************************************************************************************************/
	public static final int MDC_PULS_OXIM_PULS_RATE				=	18458;	/*                               */
	public static final int MDC_PULS_RATE_NON_INV				=	18474;	/*                               */
	public static final int MDC_PRESS_BD_NONINV					=	18948;	/*                               */
	public static final int MDC_PRESS_BD_NONINV_SYS				=	18949;	/*                               */
	public static final int MDC_PRESS_BD_NONINV_DIA				=	18950;	/*                               */
	public static final int MDC_PRESS_BD_NONINV_MEAN			=	18951;	/*                               */
	public static final int MDC_SAT_O2_QUAL 					= 	19248;	/*                               */
	public static final int MDC_TEMP_BODY						=	19292;	/*                               */
	public static final int MDC_PULS_OXIM_PERF_REL 				= 	19376;	/*                               */
	public static final int MDC_PULS_OXIM_PLETH 				= 	19380;	/*                               */
	public static final int MDC_PULS_OXIM_SAT_O2 				= 	19384;	/*                               */
	public static final int MDC_MODALITY_FAST 					= 	19508;	/*                               */
	public static final int MDC_PULS_OXIM_PULS_CHAR 			= 	19512;	/*                               */
	public static final int MDC_MODALITY_SLOW 					= 	19512;	/*                               */
	public static final int MDC_MODALITY_SPOT 					= 	19516;	/*                               */
	public static final int MDC_PULS_OXIM_DEV_STATUS 			= 	19532;	/*                               */
	public static final int MDC_CONC_GLU_GEN					=	28948;	/*                               */
	public static final int MDC_CONC_GLU_CAPILLARY_WHOLEBLOOD	=	29112;	/*                               */
	public static final int MDC_CONC_GLU_CAPILLARY_PLASMA		=	29116;	/*                               */
	public static final int MDC_CONC_GLU_VENOUS_WHOLEBLOOD		=	29120;	/*                               */
	public static final int MDC_CONC_GLU_VENOUS_PLASMA			=	29124;	/*                               */
	public static final int MDC_CONC_GLU_ARTERIAL_WHOLEBLOOD	=	29128;	/*                               */
	public static final int MDC_CONC_GLU_ARTERIAL_PLASMA		=	29132;	/*                               */
	public static final int MDC_CONC_GLU_CONTROL				=	29136;	/*                               */
	public static final int MDC_CONC_GLU_ISF					=	29140;	/*                               */
	public static final int MDC_CONC_HBA1C						=	29148;	/*                               */
	public static final int MDC_TRIG 							= 	53250;	/*                               */
	public static final int MDC_TRIG_BEAT 						= 	53251;	/*                               */
	public static final int MDC_TRIG_BEAT_MAX_INRUSH 			= 	53259;	/*                               */
	public static final int MDC_MASS_BODY_ACTUAL 				=	57664;	/*                               */
	public static final int MDC_BODY_FAT						=	57676;	/*                               */
	public static final int MDC_METRIC_NOS 						= 	61439;	/*                               */

	/*************************************************************************************************
	* From Dimensions (MDC_PART_DIM)
	**************************************************************************************************/
	public static final int MDC_DIM_PERCENT						=	544;	/* %                          	 */
	public static final int MDC_DIM_MILLI_L						=	1618;	/* mL							 */
	public static final int MDC_DIM_X_G							=	1728;	/* g							 */
	public static final int MDC_DIM_KILO_G						=	1731;	/* kg 	                         */
	public static final int MDC_DIM_MILLI_G						=	1746; 	/* mg							 */
	public static final int MDC_DIM_MILLI_G_PER_DL				=	2130;	/* mg dL-1						 */
	public static final int MDC_DIM_MIN							=	2208;	/* min    	                     */
	public static final int MDC_DIM_HR							=	2240;	/* h          	                 */
	public static final int MDC_DIM_DAY							=	2272;	/* d              	             */
	public static final int MDC_DIM_BEAT_PER_MIN				=	2720; 	/* bpm 737						 */
	public static final int MDC_DIM_KILO_PASCAL					=	3843;	/* kPa                           */
	public static final int MDC_DIM_MMHG						=	3872;	/* mmHg                          */
	public static final int MDC_DIM_MILLI_MOLE_PER_L			=	4722;	/* mmol L-1						 */
	public static final int MDC_DIM_DEGC						=	6048;	/* ºC                 	         */

	/*************************************************************************************************
	* From Communication Infrastructure (MDC_PART_INFRA)
	**************************************************************************************************/
	public static final int MDC_DEV_SPEC_PROFILE_PULS_OXIM		=	4100;	/*                               */
	public static final int MDC_DEV_SPEC_PROFILE_BP				=	4103;	/*                               */
	public static final int MDC_DEV_SPEC_PROFILE_TEMP			=	4104;	/*                               */
	public static final int MDC_DEV_SPEC_PROFILE_SCALE			=	4111;	/*                               */
	public static final int MDC_DEV_SPEC_PROFILE_GLUCOSE		=	4113;	/*                               */
	public static final int MDC_DEV_SPEC_PROFILE_HF_CARDIO		=	4137;	/*                               */
	public static final int MDC_DEV_SPEC_PROFILE_HF_STRENGTH 	=	4138;	/*                               */
	public static final int MDC_DEV_SPEC_PROFILE_AI_ACTIVITY_HUB = 4167;	/*                               */
	public static final int MDC_DEV_SPEC_PROFILE_AI_MED_MINDER	=	4168;	/*                               */
	/* Placed 256 back from the start of the last Partition: OptionalPackageIdentifiers (i.e., 8192-256). 				  */
	public static final int MDC_TIME_SYNC_NONE					=	7936;	/* no time synchronization protocol supported */
	public static final int MDC_TIME_SYNC_NTPV3					=	7937;	/* RFC 1305 1992 Mar obs: 1119,1059,958 	  */
	public static final int MDC_TIME_SYNC_NTPV4 				=	7938;	/* <under development at ntp.org>  			  */
	public static final int MDC_TIME_SYNC_SNTPV4				=	7939;	/* RFC 2030 1996 Oct          obs: 1769       */
	public static final int MDC_TIME_SYNC_SNTPV4330				=	7940;	/* RFC 4330 2006 Jan          obs: 2030,1769  */
	public static final int MDC_TIME_SYNC_BTV1					=	7941;	/* Bluetooth Medical Device Profile           */

	/*************************************************************************************************
	* From Return Codes (MDC_PART_RET_CODE)
	**************************************************************************************************/
	public static final int MDC_RET_CODE_UNKNOWN				=	1;		/* Generic error code						   */
	/* Partition MDC_PART_RET_CODE/OBJ Object errors                                          		               		   */
	public static final int MDC_RET_CODE_OBJ_BUSY         		=	1000;	/* Object is busy so cannot handle the request */
	/* Partition MDC_PART_RETURN_CODES/STORE Storage errors                                    					   		   */
	public static final int MDC_RET_CODE_STORE_EXH        		=	2000;	/* Storage such as disk is full                */
	public static final int MDC_RET_CODE_STORE_OFFLN 			=	2001;	/* Storage such as disk is offline             */


	/*********************************************************************************
	* From Personal Health Device Disease Management (MDC_PART_PHD_DM)
	**********************************************************************************/
	public static final int MDC_GLU_METER_DEV_STATUS			=	29144;	/*                               */
	public static final int MDC_CTXT_GLU_EXERCISE				=	29152;	/*                               */
	public static final int MDC_CTXT_GLU_CARB					=	29156;	/*                               */
	public static final int MDC_CTXT_GLU_CARB_BREAKFAST			=	29160;	/*                               */
	public static final int MDC_CTXT_GLU_CARB_LUNCH				=	29164;	/*                               */
	public static final int MDC_CTXT_GLU_CARB_DINNER			=	29168;	/*                               */
	public static final int MDC_CTXT_GLU_CARB_SNACK				=	29172;	/*                               */
	public static final int MDC_CTXT_GLU_CARB_DRINK				=	29176;	/*                               */
	public static final int MDC_CTXT_GLU_CARB_SUPPER			=	29180;	/*                               */
	public static final int MDC_CTXT_GLU_CARB_BRUNCH			=	29184;	/*                               */
	public static final int MDC_CTXT_MEDICATION					=	29188;	/*                               */
	public static final int MDC_CTXT_MEDICATION_RAPIDACTING		=	29192;	/*                               */
	public static final int MDC_CTXT_MEDICATION_SHORTACTING		=	29196;	/*                               */
	public static final int MDC_CTXT_MEDICATION_INTERMEDIATEACTING	=	29200;	/*                           */
	public static final int MDC_CTXT_MEDICATION_LONGACTING		=	29204;	/*                               */
	public static final int MDC_CTXT_MEDICATION_PREMIX			=	29208;	/*                               */
	public static final int MDC_CTXT_GLU_HEALTH					=	29212;	/*                               */
	public static final int MDC_CTXT_GLU_HEALTH_MINOR			=	29216;	/*                               */
	public static final int MDC_CTXT_GLU_HEALTH_MAJOR			=	29220;	/*                               */
	public static final int MDC_CTXT_GLU_HEALTH_MENSES			=	29224;	/*                               */
	public static final int MDC_CTXT_GLU_HEALTH_STRESS			=	29228;	/*                               */
	public static final int MDC_CTXT_GLU_HEALTH_NONE			=	29232;	/*                               */
	public static final int MDC_CTXT_GLU_SAMPLELOCATION			=	29236;	/*                               */
	public static final int MDC_CTXT_GLU_SAMPLELOCATION_FINGER	=	29240;	/*                               */
	public static final int MDC_CTXT_GLU_SAMPLELOCATION_AST		=	29244;	/*                               */
	public static final int MDC_CTXT_GLU_SAMPLELOCATION_EARLOBE =	29248;	/*                               */
	public static final int MDC_CTXT_GLU_SAMPLELOCATION_CTRLSOLUTION =	29252;	/*                           */
	public static final int MDC_CTXT_GLU_MEAL					=	29256;	/*                               */
	public static final int MDC_CTXT_GLU_MEAL_PREPRANDIAL		=	29260;	/*                               */
	public static final int MDC_CTXT_GLU_MEAL_POSTPRANDIAL		=	29264;	/*                               */
	public static final int MDC_CTXT_GLU_MEAL_FASTING			=	29268;	/*                               */
	public static final int MDC_CTXT_GLU_MEAL_CASUAL			=	29272;	/*                               */
	public static final int MDC_CTXT_GLU_TESTER					=	29276;	/*                               */
	public static final int MDC_CTXT_GLU_TESTER_SELF			=	29280;	/*                               */
	public static final int MDC_CTXT_GLU_TESTER_HCP				=	29284;	/*                               */
	public static final int MDC_CTXT_GLU_TESTER_LAB				=	29288;	/*                               */
}
