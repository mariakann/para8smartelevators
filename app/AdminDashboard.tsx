import React from "react";
import { ScrollView, StyleSheet, Text, View } from "react-native";

// First version of the admin screen: only the floor map, with fixed data.
// TODO: real positions and stops from the elevators table once the api is ready

type ElevatorStatus = "IDLE" | "UP" | "DOWN";

interface Elevator {
    id: string;
    capacity: number;
    currentFloor: number;
    status: ElevatorStatus;
    stops: number[];
}

// same floors as the floors table, 0 is the ground floor
const minFloor = 0;
const maxFloor = 9;

// from this floor and up is_restricted is true in the floors table
const firstRestrictedFloor = 7;

// the map shows the top floor first
const floors = Array.from({ length: maxFloor - minFloor + 1 }, (_, index) => ({
    id: index,
    name: index === 0 ? "Ισόγειο" : `Όροφος ${index}`,
    isRestricted: index >= firstRestrictedFloor,
})).reverse();

// example data until we can ask the central system
const elevators: Elevator[] = [
    { id: "1", capacity: 8, currentFloor: 0, status: "IDLE", stops: [] },
    { id: "2", capacity: 8, currentFloor: 3, status: "UP", stops: [5, 8] },
    { id: "3", capacity: 8, currentFloor: 7, status: "DOWN", stops: [2, 0] },
    { id: "4", capacity: 8, currentFloor: 9, status: "IDLE", stops: [] },
    { id: "5", capacity: 8, currentFloor: 1, status: "UP", stops: [4] },
];

const rowHeight = 22;

export default function AdminDashboard() {
    return (
        <ScrollView contentContainerStyle={styles.container}>
            <Text style={styles.title}>ELEVATE</Text>
            <Text style={styles.subtitle}>
                Admin Screen ({minFloor}-{maxFloor} Όροφοι / {elevators.length} Ανελκυστήρες)
            </Text>
            <Text style={styles.heading}>Χάρτης Ορόφων (δεδομένα παραδείγματος)</Text>

            <View style={styles.card}>
                <View style={styles.row}>
                    <Text style={[styles.floorColumn, styles.headerText]}>Όροφος</Text>
                    {elevators.map((elevator) => (
                        <Text key={elevator.id} style={[styles.cell, styles.headerText]}>
                            {elevator.id}
                        </Text>
                    ))}
                </View>

                {floors.map((floor) => (
                    <View key={floor.id} style={[styles.row, floor.id % 2 === 0 && styles.evenRow]}>
                        <View style={styles.floorColumn}>
                            <Text style={styles.floorNumber}>{floor.id}</Text>
                            <Text
                                style={[styles.floorName, floor.isRestricted && styles.restricted]}
                                numberOfLines={1}
                            >
                                {floor.name}
                            </Text>
                        </View>
                        {elevators.map((elevator) => (
                            <MapCell key={elevator.id} elevator={elevator} floor={floor.id} />
                        ))}
                    </View>
                ))}
            </View>
        </ScrollView>
    );
}

function MapCell({ elevator, floor }: { elevator: Elevator; floor: number }) {
    if (elevator.currentFloor === floor) {
        return (
            <View style={styles.cell}>
                <View style={styles.car}>
                    <Text style={styles.carText}>
                        {elevator.id}
                        {arrow(elevator.status)}
                    </Text>
                </View>
            </View>
        );
    }

    return (
        <View style={styles.cell}>
            {elevator.stops.includes(floor) && <View style={styles.stopDot} />}
        </View>
    );
}

function arrow(status: ElevatorStatus): string {
    if (status === "UP") {
        return "▲";
    }
    if (status === "DOWN") {
        return "▼";
    }
    return "";
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
    row: {
        flexDirection: "row",
        alignItems: "center",
        height: rowHeight,
    },
    evenRow: {
        backgroundColor: "#2c2c2c",
    },
    headerText: {
        fontSize: 12,
        fontWeight: "bold",
        color: "#94a3b8",
    },
    floorColumn: {
        flex: 1,
        flexDirection: "row",
        alignItems: "center",
    },
    floorNumber: {
        width: 24,
        textAlign: "right",
        marginRight: 6,
        fontWeight: "bold",
        color: "#ffffff",
    },
    floorName: {
        flex: 1,
        fontSize: 12,
        color: "#cbd5e1",
    },
    restricted: {
        color: "rgb(225, 207, 204)",
        fontWeight: "bold",
    },
    cell: {
        width: 38,
        height: rowHeight,
        alignItems: "center",
        justifyContent: "center",
        textAlign: "center",
        borderLeftWidth: 1,
        borderLeftColor: "#2c2c2c",
    },
    car: {
        width: 32,
        height: rowHeight - 4,
        borderRadius: 4,
        alignItems: "center",
        justifyContent: "center",
        backgroundColor: "#2563eb",
    },
    carText: {
        color: "#ffffff",
        fontSize: 11,
        fontWeight: "bold",
    },
    stopDot: {
        width: 6,
        height: 6,
        borderRadius: 3,
        backgroundColor: "#38bdf8",
    },
});
