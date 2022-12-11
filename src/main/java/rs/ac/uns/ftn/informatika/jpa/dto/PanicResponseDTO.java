package rs.ac.uns.ftn.informatika.jpa.dto;

import rs.ac.uns.ftn.informatika.jpa.model.Passenger;
import rs.ac.uns.ftn.informatika.jpa.model.Path;
import rs.ac.uns.ftn.informatika.jpa.model.Ride;
import rs.ac.uns.ftn.informatika.jpa.model.User;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

public class PanicResponseDTO {

// [
//        {
//                "id": 10,
//                "user": {
//                    "name": "Pera",
//                    "surname": "Perić",
//                    "profilePicture": "U3dhZ2dlciByb2Nrcw==",
//                    "telephoneNumber": "+381123123",
//                    "email": "pera.peric@email.com",
//                    "address": "Bulevar Oslobodjenja 74"
//                    },
//                "ride": {
//                    "id": 123,
//                    "startTime": "2017-07-21T17:32:28Z",
//                    "endTime": "2017-07-21T17:45:14Z",
//                    "totalCost": 1235,
//                    "driver": {
//                        "id": 123,
//                        "email": "user@example.com"
//                    },
//                    "passengers": [
//                        {
//                            "id": 123,
//                            "email": "user@example.com"
//                        }
//                    ],
//                    "estimatedTimeInMinutes": 5,
//                    "vehicleType": "STANDARDNO",
//                    "babyTransport": true,
//                    "petTransport": true,
//                    "rejection": {
//                         "reason": "Ride is canceled due to previous problems with the passenger",
//                        "timeOfRejection": "2022-11-25T17:32:28Z"
//                        },
//                     "locations": [
//                        {
//                            "departure": {
//                                "address": "Bulevar oslobodjenja 46",
//                                "latitude": 45.267136,
//                                "longitude": 19.833549
//                            },
//                            "destination": {
//                                "address": "Bulevar oslobodjenja 46",
//                                "latitude": 45.267136,
//                                "longitude": 19.833549
//                            }
//                        }
//                    ]
//                },
//            "time": "2022-12-11T20:19:29.285Z",
//            "reason": "Driver is drinking while driving"
//        }


    private Long id;
    private PanicUserResponseDTO user;
    private PanicRideResponseDTO ride;
    private Date time;
    private String reason;

    public PanicResponseDTO(Long id, PanicUserResponseDTO user, PanicRideResponseDTO ride, Date time, String reason) {
        this.id = id;
        this.user = user;
        this.ride = ride;
        this.time = time;
        this.reason = reason;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PanicUserResponseDTO getUser() {
        return user;
    }

    public void setUser(PanicUserResponseDTO user) {
        this.user = user;
    }

    public PanicRideResponseDTO getRide() {
        return ride;
    }

    public void setRide(PanicRideResponseDTO ride) {
        this.ride = ride;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

}
