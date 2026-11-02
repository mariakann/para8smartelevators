import React, { useState } from "react";
import {
    View,
    Text,
    TextInput,
    TouchableOpacity,
    StyleSheet,
    ScrollView,
    Alert,
} from "react-native";

const minFloor = 0;
const maxFloor = 9;
const totalElevators = 5;

export default function App() {
    
    /*
    AI-Tool: ChatGpt
     AI-Model: gpt-4o
     AI-Task: State definition and array mapping for floors in EmployeeScreen
     AI-Prompt-1: Πώς να ορίσω τα states και τα array.from για τους ορόφους
     AI-Tokens: ~400
     AI-Role: Developer Assistant
    */
    
    const [floors] = useState(
        Array.from({ length: maxFloor - minFloor + 1 }, (_, index) => ({
            id: index,
            name: index === 0 ? "ΙΣΟΓΕΙΟ (Ground)" : `ΟΡΟΦΟΣ ${index}`,
            isRestricted: index === minFloor || index === maxFloor,
        }))
    );
    
    const [elevators] = useState(
        Array.from({ length: totalElevators }, (_, index) => ({
            id: index + 1,
            capacity: 8,
            currentFloor: 0,
            status: "idle",
        }))
    );
    
    const [employeeId, setEmployeeId] = useState("");
    const [originFloor, setOriginFloor] = useState("");
    const [destinationFloor, setDestinationFloor] = useState("");
    const [priority, setPriority] = useState("3");
    const [meetingCode, setMeetingCode] = useState("");

    const isRestrictedFloor = (floor) => {
        return floor === minFloor || floor === maxFloor;
    };
    
    const handleSubmitting = () => {

        const employee = employeeId.trim();
        const originInput = originFloor.trim();
        const destinationInput = destinationFloor.trim();
        const meeting = meetingCode.trim();
        
        if (originInput === "" || destinationInput === "" || priority === "") {
            Alert.alert("Παρακαλώ συμπληρώστε τους ορόφους και την προτεραιότητα!");
            return;
        }
        
        if (!/^\d+$/.test(originInput) || !/^\d+$/.test(destinationInput)) {
            Alert.alert("Ο όροφος ΔΕΝ είναι έγκυρος.", "\n\n Οι όροφοι πρέπει να είναι ακέραιοι αριθμοί.");
            return;
        }

        const origin = Number(originInput);
        const destination = Number(destinationInput);

        if (origin < minFloor || origin > maxFloor || destination < minFloor || destination > maxFloor) {
            Alert.alert("Ο όροφος ΔΕΝ είναι έγκυρος.", `\n\n Οι όροφοι πρέπει να είναι μεταξύ ${minFloor} και ${maxFloor}.`);
            return;
        }
        
        if (origin === destination) {
            Alert.alert("Η διαδρομή ΔΕΝ είναι έγκυρη.", `\n\n Ο όροφος αφετηρίας πρέπει να διαφέρει από τον όροφο προορισμού.`);
            return;
        }

        if (!["1", "2", "3"].includes(priority.trim())) {
            Alert.alert("Η προτεραιότητα ΔΕΝ είναι έγκυρη.", "\n\n Επιλέξτε 1 για Emergency, 2 για Meeting ή 3 για Normal.");
            return;
        }

        const priorityLevel = Number(priority.trim());

        const restrictedFloor = isRestrictedFloor(origin) || isRestrictedFloor(destination);

        const employeeRestricted = priorityLevel !== 2 || restrictedFloor;

        if (employeeRestricted && employee === "") {
            Alert.alert("Υποχρεωτικό ID Εργαζομένου", restrictedFloor ? "Η διαδρομή περιλαμβάνει περιορισμένο όροφο. Παρακαλώ εισάγετε το ID Εργαζομένου." : "Για αίτημα Emergency και Normal απαιτείται ID Εργαζομένου.");
            return;
        }

        if (priorityLevel === 2 && meeting === "") {
            Alert.alert("Υποχρεωτικός Κωδικός Meeting", "Για αίτημα Meeting συμπληρώστε τον Κωδικό Meeting.");
            return;
        }

        let requestType = "";
        switch (priorityLevel) {
            case 1:
                requestType = "Emergency";
                break;
            case 2:
                requestType = "Meeting";
                break;
            case 3:
                requestType = "Normal";
                break;
        }

        const employeeText = employee !== "" ? employee : "ΔΕΝ ΒΡΕΘΗΚΕ";

        let resultMessage = `Tύπος Αιτήματος: ${requestType}\nID Εργαζομένου: ${employeeText}\nΑπό Όροφο: ${origin} -> Διαδρομή: ${destination}\nΠροτεραιότητα: ${priorityLevel}`;

        if (priorityLevel === 2) {
            resultMessage += `\nΚωδικός Meeting: ${meeting}`;
        }

        if (restrictedFloor) {
            resultMessage += `\nΠΡΟΣΟΧΗ: Η διαδρομή περιλαμβάνει όροφο περιορισμένης πρόσβασης!`;
        }
        
        Alert.alert(
            "Το αίτημα καταχωρήθηκε ΕΠΙΤΥΧΩΣ!!",
            resultMessage
        );
        
        setEmployeeId("");
        setOriginFloor("");
        setDestinationFloor("");
        setPriority("3");
        setMeetingCode("");
    };
    
    return (
    
     /*
     AI-Tool: ChatGpt / VS Code Corilot
     AI-Model: gpt-4o
     AI-Task: Creation and formatting of ScrollingView with floor
     AI-Prompt-1: Δημιούργησε ένα ScrollView σε React Native για την εμφάνιση ορόφων
     AI-Tokens: ~500
     AI-Role: Developer Assistant
     */

    <ScrollView contentContainerStyle={styles.container}>
        <Text style={styles.title}>ELEVATE</Text>
        <Text style={styles.subtitle}>Employee Screen (0-9 Όροφοι / 5 Ανελκυστήρες)</Text>
        <Text style={styles.heading}>Διαθέσιμοι Όροφοι</Text>
        {floors.map((floor) => (
            <View key={floor.id} style={styles.card}>
                <Text style={styles.cardTitle}>{floor.name} (ID: {floor.id})</Text>
                <Text style={styles.text}>
                Πρόσβαση:{" "}
                    <Text style={floor.isRestricted ? styles.restricted : styles.normal}>
                        {floor.isRestricted ? "Περιορισμένη" : "Κανονική"}
                        </Text>
                        </Text>
                        </View>
                    ))}
                    
                    <Text style={styles.heading}>Ανελκυστήρες</Text>
                    {elevators.map((elevator) => (
                        <View key={elevator.id} style={styles.card}>
                            <Text style={styles.cardTitle}>Ανελκυστήρας {elevator.id}</Text>
                            <Text style={styles.text}>Χωρητικότητα: {elevator.capacity} άτομα</Text>
                            <Text style={styles.text}>Τρέχων Όροφος: {elevator.currentFloor}</Text>
                            <Text style={styles.text}>
                                Κατάσταση:{" "}
                                <Text style={styles.statusText}>{elevator.status}</Text>
                                </Text>
                            </View>
                        ))}
                        
                        <Text style={styles.heading}>Νέο Αίτημα Μετακίνησης</Text>

                        <Text style={styles.label}>Επίπεδο Προτεραιότητας</Text>
                        <Text style={styles.text}>1 = Emergency | 2 = Meeting | 3 = Normal</Text>
                        <TextInput
                        style={styles.input}
                        value={priority}
                        onChangeText={setPriority}
                        keyboardType="numeric"
                        placeholder="1, 2 ή 3"
                        placeholderTextColor="#666"
                        />

                        {priority.trim() === "2" && (
                            <>
                            <Text style={styles.label}>Κωδικός Meeting (Υποχρεωτικό)
                            </Text>
                            <TextInput
                            style={styles.input}
                            value={meetingCode}
                            onChangeText={setMeetingCode}
                            placeholder="πχ CODE-123"
                            placeholderTextColor="#666"
                            keyboardType="numeric"
                            />
                            </>
                        )}

                        <Text style={styles.label}>ID Εργαζομένου</Text>
                        <TextInput 
                        style={styles.input}
                        value={employeeId}
                        onChangeText={setEmployeeId}
                        keyboardType="numeric"
                        placeholder="πχ 101"
                        placeholderTextColor="#666"
                        />
                        
                        <Text style={styles.label}>Υποχρεωτικό για Emergency και Normal διαδρομές, καθώς και για διαδρομές που περιλαμβάνουν όροφο περιορισμένης πρόσβασης. Προαιρετικό για άλλους τύπους αιτημάτων.</Text>

                        <Text style={styles.label}>Όροφος Αφετηρίας (0-9)</Text>
                        <TextInput
                        style={styles.input}
                        value={originFloor}
                        onChangeText={setOriginFloor}
                        keyboardType="numeric"
                        placeholder="πχ 0"
                        placeholderTextColor="#666"
                        />
                        
                        <Text style={styles.label}>Όροφος Προορισμού (0-9)</Text>
                        <TextInput
                        style={styles.input}
                        value={destinationFloor}
                        onChangeText={setDestinationFloor}
                        keyboardType="numeric"
                        placeholder="πχ 4"
                        placeholderTextColor="#666"
                        />
                        
                        
                        
                        <TouchableOpacity style={styles.button} onPress={handleSubmitting}>
                            <Text style={styles.buttonText}>Υποβολή Αιτήματος</Text>
                            </TouchableOpacity>
                            </ScrollView>
                            );
                        }
                        
                        const styles = StyleSheet.create({
                            container: {
                                padding: 20,
                                paddingTop: 50,
                                backgroundColor: "#121212",
                                flexGrow: 1,
                            },
                            title: {
                                fontSize: 32,
                                fontWeight: "bold",
                                textAlign: "center",
                                color: "#2387b9",
                                letterSpacing: 1,
                            },
                            subtitle: {
                                fontSize: 14,
                                textAlign: "center",
                                marginBottom: 25,
                                color: "#94a3b8",
                            },
                            heading: {
                                fontSize: 20,
                                fontWeight: "bold",
                                marginTop: 25,
                                marginBottom: 12,
                                color: "#2996c4",
                            },
                            card: {
                                backgroundColor: "#1e1e1e",
                                padding: 16,
                                borderRadius: 10,
                                marginBottom: 12,
                                borderWidth: 1,
                                borderColor: "#2c2c2c",
                            },
                            cardTitle: {
                                fontSize: 16,
                                fontWeight: "bold",
                                marginBottom: 6,
                                color: "#ffffff",
                            },
                            text: {
                                fontSize: 14,
                                color: "#cbd5e1",
                                marginTop: 4,
                                lineHeight: 20,
                            },
                            restricted: {
                                color: "rgb(225, 207, 204)",
                                fontWeight: "bold",
                            },
                            normal: {
                                color: "rgba(205, 231, 228, 0.53)",
                                fontWeight: "bold",
                            },
                            statusText: {
                                color: "#38bdf8",
                                fontStyle: "italic",
                            },
                            label: {
                                fontSize: 15,
                                marginTop: 14,
                                marginBottom: 6,
                                fontWeight: "600",
                                color: "#94a3b8",
                            },
                            input: {
                                backgroundColor: "#1e1e1e",
                                borderWidth: 1,
                                borderColor: "#334155",
                                borderRadius: 8,
                                padding: 14,
                                fontSize: 16,
                                color: "#ffffff",
                            },
                            button: {
                                backgroundColor: "#2563eb",
                                padding: 16,
                                borderRadius: 8,
                                alignItems: "center",
                                marginTop: 30,
                                marginBottom: 40,
                                shadowColor: "#2563eb",
                                shadowOpacity: 0.4,
                                shadowRadius: 8,
                                elevation: 4,
                            },
                            buttonText: {
                                color: "#ffffff",
                                fontSize: 16,
                                fontWeight: "bold",
                                letterSpacing: 0.5,
                            },
}); 