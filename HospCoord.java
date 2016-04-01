package hospitalcore;

import java.util.*;
import m256people.*;
import m256date.*;
import java.io.*;

/**
 * The coordinating class for the Hospital core system.
 */
public class HospCoord implements java.io.Serializable
{
    //links
    
    /**
     * a collection of all Ward objects
     */
    private Collection<Ward> wards;
    
    /**
     * a collection of all Team objects
     */
    private Collection<Team> teams;
    
    
    //constructor
    
    /**
     * Initialises a new HospCoord object.
     */
    public HospCoord()
    {
        teams = new HashSet<Team>();
        wards = new HashSet<Ward>();
    }
    
    
    //public protocol
    
    /**
     * Records the admission of a patient with the given attributes and cared
     * for by the given team if there is a ward of the appropriate type with
     * free beds.<p>
     * If there is no Ward object of the appropriate type with at least one free bed
     * then null is returned.<p>
     * Otherwise a new Patient object, aPatient, is created with the supplied attribute values, 
     * and age according to aDate and:<p>
     * <ol>
     *   <li> 
     *      aPatient is linked to aWard, where aWard is a Ward object of the appropriate type
     *      with the greatest number of free beds, and numberOfFreeBeds of aWard is decremented.
     *   </li>
     *   <li> 
     *      aPatient is linked to aTeam
     *   </li>
     *   <li> 
     *      aPatient is linked to the ConsultantDoctor object that is linked to aTeam
     *   </li>
     *   <li>
     *      aWard is returned.
     *   </li>
     * </ol>
     *
     * @param aName the name of the patient
     * @param aSex the sex of the patient
     * @param aDate the date of birth of the patient
     * @param aTeam a team
     *
     * @return the Ward object to which the new Patient object is linked
     *         or null if there is no suitable Ward object
     */
    public Ward admit(Name aName, Sex aSex, M256Date aDate, Team aTeam)
    {
        Ward theWard = null;
        int mostBeds = 0;
        int freeBeds = 0;
        for (Ward aWard : wards)
        {
            if (aWard.getType() == aSex)
            {
                freeBeds = aWard.getNumberOfFreeBeds();
                if (freeBeds > mostBeds)
                {
                    mostBeds = freeBeds;
                    theWard = aWard;
                }
            }
        }
        if (theWard != null)
        {
            Patient thePatient = new Patient(aName, aSex, aDate);
            thePatient.admit(theWard, aTeam);
        }
        return theWard;
    }
    
    
    /**
     * Returns a string representation of all teams and wards.
     *
     * @return a String object representing the receiver
     */
    public String toString()
    {
        return teams.toString() + wards.toString();
    }    
}