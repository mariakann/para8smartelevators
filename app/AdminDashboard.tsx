import { ScrollView, StyleSheet, Text, View } from 'react-native';

// First version of the admin screen: only the floor map, with fixed data.
// TODO: real positions and stops from the elevators table once the api is ready

type Direction = 'UP' | 'DOWN' | 'IDLE';

interface Car {
  id: number;
  floor: number;
  direction: Direction;
  stops: number[];
}

// same floors as the floors table, 0 is the ground floor
const MIN_FLOOR = 0;
const MAX_FLOOR = 10;

// floors that need an admin badge or a meeting code
const RESTRICTED = [MIN_FLOOR, MAX_FLOOR];

// example data until we can ask the central system
const CARS: Car[] = [
  { id: 1, floor: 0, direction: 'IDLE', stops: [] },
  { id: 2, floor: 3, direction: 'UP', stops: [5, 8] },
  { id: 3, floor: 7, direction: 'DOWN', stops: [2, 0] },
  { id: 4, floor: 10, direction: 'IDLE', stops: [] },
  { id: 5, floor: 1, direction: 'UP', stops: [4] },
];

const ROW_HEIGHT = 22;

export default function AdminDashboard() {
  const floors: number[] = [];
  for (let floor = MAX_FLOOR; floor >= MIN_FLOOR; floor--) {
    floors.push(floor);
  }

  return (
    <ScrollView contentContainerStyle={styles.page}>
      <Text style={styles.title}>Πίνακας ελέγχου</Text>
      <Text style={styles.subtitle}>Χάρτης ορόφων (δεδομένα παραδείγματος)</Text>

      <View style={styles.map}>
        <View style={styles.row}>
          <Text style={[styles.floorColumn, styles.headerText]}>Όροφος</Text>
          {CARS.map((car) => (
            <Text key={car.id} style={[styles.cell, styles.headerText]}>
              {car.id}
            </Text>
          ))}
        </View>

        {floors.map((floor) => (
          <View key={floor} style={[styles.row, floor % 2 === 0 && styles.evenRow]}>
            <View style={styles.floorColumn}>
              <Text style={styles.floorNumber}>{floor}</Text>
              <Text
                style={[styles.floorLabel, RESTRICTED.includes(floor) && styles.restricted]}
                numberOfLines={1}
              >
                {floorLabel(floor)}
              </Text>
            </View>
            {CARS.map((car) => (
              <MapCell key={car.id} car={car} floor={floor} />
            ))}
          </View>
        ))}
      </View>
    </ScrollView>
  );
}

function MapCell({ car, floor }: { car: Car; floor: number }) {
  if (car.floor === floor) {
    return (
      <View style={styles.cell}>
        <View style={styles.car}>
          <Text style={styles.carText}>
            {car.id}
            {arrow(car.direction)}
          </Text>
        </View>
      </View>
    );
  }

  return (
    <View style={styles.cell}>
      {car.stops.includes(floor) && <View style={styles.stopDot} />}
    </View>
  );
}

function floorLabel(floor: number): string {
  return floor === 0 ? 'Ισόγειο' : 'Όροφος ' + floor;
}

function arrow(direction: Direction): string {
  if (direction === 'UP') {
    return '▲';
  }
  if (direction === 'DOWN') {
    return '▼';
  }
  return '';
}

const styles = StyleSheet.create({
  page: {
    padding: 16,
  },
  title: {
    fontSize: 20,
    fontWeight: '700',
    color: '#1d2733',
  },
  subtitle: {
    fontSize: 12,
    color: '#6b7785',
    marginBottom: 12,
  },
  map: {
    maxWidth: 520,
    padding: 12,
    borderRadius: 8,
    borderWidth: 1,
    borderColor: '#dde3ea',
    backgroundColor: '#fff',
  },
  row: {
    flexDirection: 'row',
    alignItems: 'center',
    height: ROW_HEIGHT,
  },
  evenRow: {
    backgroundColor: '#f4f6f8',
  },
  headerText: {
    fontSize: 12,
    fontWeight: '700',
    color: '#6b7785',
  },
  floorColumn: {
    flex: 1,
    flexDirection: 'row',
    alignItems: 'center',
  },
  floorNumber: {
    width: 28,
    textAlign: 'right',
    marginRight: 8,
    fontWeight: '700',
    color: '#1d2733',
  },
  floorLabel: {
    flex: 1,
    fontSize: 12,
    color: '#1d2733',
  },
  restricted: {
    color: '#c0392b',
  },
  cell: {
    width: 44,
    height: ROW_HEIGHT,
    alignItems: 'center',
    justifyContent: 'center',
    textAlign: 'center',
    borderLeftWidth: 1,
    borderLeftColor: '#dde3ea',
  },
  car: {
    width: 36,
    height: ROW_HEIGHT - 4,
    borderRadius: 4,
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: '#1f3a5f',
  },
  carText: {
    color: '#fff',
    fontSize: 11,
    fontWeight: '700',
  },
  stopDot: {
    width: 6,
    height: 6,
    borderRadius: 3,
    backgroundColor: '#1f3a5f',
  },
});
