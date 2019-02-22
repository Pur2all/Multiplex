package classes;

import managers.DataManager;
import managers.GenericManager;
import managers.AccountsManager;
import managers.ReservationManager;
import managers.DiscountsManager;

/**
 * This class represent an abstraction of a real Cinema with all the parts of him
 *
 * @author Francesco Migliaro
 */
public class Cinema
{
    /**
     * Initialize a newly created Cinema loading all the data that serve to him
     */
    public Cinema()
    {
        dataManager=new DataManager();
        accountsManager=new AccountsManager(dataManager.getUsers());
        filmsManager=new GenericManager<Film>(dataManager.getFilms());
        hallsManager=new GenericManager<Hall>(dataManager.getHalls());
        discountsManager=new DiscountsManager(dataManager.getDiscounts(), dataManager.getDiscountPoliciesAreActive());
        weeklyProgramManager=new GenericManager<Show>(dataManager.getWeeklyProgram());
        reservationManager=null;
    }

    /**
     * Return the login manager
     *
     * @return login manager
     */
    public AccountsManager getLoginManager()
    {
        return accountsManager;
    }

    /**
     * Return the generic manager for class Film
     *
     * @return generic manager for class Film
     */
    public GenericManager<Film> getFilmsManager()
    {
        return filmsManager;
    }

    /**
     * Return the generic manager for class Hall
     *
     * @return generic manager for class Hall
     */
    public GenericManager<Hall> getHallsManager()
    {
        return hallsManager;
    }

    /**
     * Return the sales manager
     *
     * @return sales manager
     */
    public DiscountsManager getDiscountsManager()
    {
        return discountsManager;
    }

    /**
     * Return the generic manager for class Show
     *
     * @return generic manager for class Show
     */
    public GenericManager<Show> getWeeklyProgramManager()
    {
        return weeklyProgramManager;
    }

    /**
     * Return the reservation manager
     * <br><strong> NOTE: </strong> If the reservation manager isn't be first setted this method will return null
     *
     * @return reservation manager
     */
    public ReservationManager getReservationsManager()
    {
        return reservationManager;
    }

    /**
     * Set the reservation manager
     *
     * @param aClient The client from load and store the reservations
     */
    public void setReservationsManager(Client aClient)
    {
        reservationManager=new ReservationManager(aClient);
        for(int i=0; i<reservationManager.getNumberOfReservation(); i++)
        {
            Hall tempHall=reservationManager.getReservation(i).getShow().getHall();

            for(int k=0; k<weeklyProgramManager.getNumberOfElement(); k++)
            {
                Hall currentHall=weeklyProgramManager.getElement(k).getHall();

                if(tempHall.isSameHall(currentHall))
                {
                    Reservation tempReservation=reservationManager.getReservation(i);
                    Show tempShow=tempReservation.getShow();

                    tempShow.setHall(currentHall);
                    tempReservation.setShow(tempShow);
                    reservationManager.setReservation(i, tempReservation);
                }
            }
        }
    }

    /**
     * Save the client reservation
     * <br><strong> NOTE: </strong> This method must be called first of to call saveData if you want to save the client reservations
     *
     * @param aClient The client who has to save reservations
     */
    public void saveClientReservation(Client aClient)
    {
        accountsManager.setUserLogged(aClient);
    }

    /**
     * Save the Cinema data into a file
     *
     * @param isSalePolicyActive A flag that indicates if the discount policy has to remain active or not
     */
    public void saveData(Boolean isSalePolicyActive)
    {
        dataManager.setDiscountPoliciesAreActive(isSalePolicyActive);
        dataManager.saveData();
    }

    private DataManager dataManager;
    private AccountsManager accountsManager;
    private GenericManager<Film> filmsManager;
    private GenericManager<Hall> hallsManager;
    private DiscountsManager discountsManager;
    private GenericManager<Show> weeklyProgramManager;
    private ReservationManager reservationManager;
}
