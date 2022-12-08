package rs.ac.uns.ftn.informatika.jpa.dto;

import rs.ac.uns.ftn.informatika.jpa.model.Passenger;
import rs.ac.uns.ftn.informatika.jpa.model.Path;
import rs.ac.uns.ftn.informatika.jpa.model.Ride;
import rs.ac.uns.ftn.informatika.jpa.model.User;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

public class PanicResponseDTO {

    private Long id;

    private int totalCount;
    private User user;
    private Ride ride;

    private ArrayList<Passenger> passengers;
    private ArrayList<Path> paths;
    private Date time;
    private String reason;

//    {
//        "totalCount": 243,
//            "results": [
//        {
//            "id": 10,
//                "user": {
//            "name": "Pera",
//                    "surname": "Perić",
//                    "profilePicture": "U3dhZ2dlciByb2Nrcw==",
//                    "telephoneNumber": "+381123123",
//                    "email": "pera.peric@email.com",
//                    "address": "Bulevar Oslobodjenja 74"
//        },
//            "ride": {
//            "id": 123,
//                    "startTime": "2017-07-21T17:32:28Z",
//                    "endTime": "2017-07-21T17:45:14Z",
//                    "totalCost": 1235,
//                    "driver": {
//                "id": 123,
//                        "email": "user@example.com"
//            },
//            "passengers": [
//            {
//                "id": 123,
//                    "email": "user@example.com"
//            }
//        ],
//            "estimatedTimeInMinutes": 5,
//                    "vehicleType": "STANDARDNO",
//                    "babyTransport": true,
//                    "petTransport": true,
//                    "rejection": {
//                "reason": "Ride is canceled due to previous problems with the passenger",
//                        "timeOfRejection": "2022-11-25T17:32:28Z"
//            },
//            "locations": [
//            {
//                "departure": {
//                "address": "Bulevar oslobodjenja 46",
//                        "latitude": 45.267136,
//                        "longitude": 19.833549
//            },
//                "destination": {
//                "address": "Bulevar oslobodjenja 46",
//                        "latitude": 45.267136,
//                        "longitude": 19.833549
//            }
//            }
//        ]
//        },
//            "time": "2022-12-08T23:11:54.554Z",
//                "reason": "Driver is drinking while driving"
//        }
//  ]
//    }


    public PanicResponseDTO() {
    }

    public PanicResponseDTO(Long id, int totalCount, Date time, String reason) {
        this.id = id;
        this.totalCount = totalCount;
        this.time = time;
        this.reason = reason;
        this.ride=null;
        this.passengers=null;
        this.paths=null;
        this.user=null;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Ride getRide() {
        return ride;
    }

    public void setRide(Ride ride) {
        this.ride = ride;
    }

    public ArrayList<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(ArrayList<Passenger> passengers) {
        this.passengers = passengers;
    }

    public ArrayList<Path> getPaths() {
        return paths;
    }

    public void setPaths(ArrayList<Path> paths) {
        this.paths = paths;
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
