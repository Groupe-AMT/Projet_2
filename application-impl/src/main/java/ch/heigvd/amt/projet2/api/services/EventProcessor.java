package ch.heigvd.amt.projet2.api.services;

import ch.heigvd.amt.projet2.entities.ApplicationEntity;
import ch.heigvd.amt.projet2.entities.EndUserEntity;
import ch.heigvd.amt.projet2.entities.EventEntity;
import ch.heigvd.amt.projet2.repositories.EndUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EventProcessor{

    @Autowired
    EndUserRepository endUserRepository;

    public void processEvent(ApplicationEntity app, EventEntity event) {
        EndUserEntity user = endUserRepository.findByIDUserAndAppName(event.getIDUser(), app.getName());

        if (user == null) {
            user = new EndUserEntity();
            user.setIDUser(event.getIDUser());
            user.setUserName(event.getUserName());
            user.setAppName(app.getName());

            user.setNbEvents(1);
            switch (event.getAction()){
                case "message":
                    user.setNbMessages(1);
                    break;
                case "vote":
                    user.setNbVotes(1);
                    break;
            }
            endUserRepository.save(user);
        } else {
            user.setNbEvents(user.getNbEvents() + 1);
            switch (event.getAction()){
                case "message":
                    user.setNbMessages(user.getNbMessages()+1);
                    break;
                case "vote":
                    user.setNbVotes(user.getNbVotes()+1);
                    break;
            }
        }

    }
}
