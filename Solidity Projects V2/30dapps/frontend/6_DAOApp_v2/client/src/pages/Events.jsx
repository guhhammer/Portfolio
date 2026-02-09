import { useEffect, useState } from 'react';
import { Container, Typography, Box, Paper } from '@mui/material';
import eventsData from '../constants/events.json';
import InfoCard from '../components/InfoCard';

function Events() {
  const [events, setEvents] = useState([]);

  useEffect(() => {
    // Convert the object to a sorted array
    const sorted = Object.entries(eventsData)
      .sort((a, b) => b[1].timestampMiddleware - a[1].timestampMiddleware)
      .map(([_, value]) => value);
    setEvents(sorted);
  }, []);

  return (
    <>
      <InfoCard />
    
      <Container sx={{ mt: 5 }}>
        <Typography variant="h4" gutterBottom textAlign="center">
          Events
        </Typography>

        <Paper elevation={3} sx={{ padding: 3 }}>
          {events.map((event, index) => (
            <Box
              key={index}
              sx={{
                display: 'grid',
                gridTemplateColumns: '180px 160px 1fr',
                alignItems: 'center',
                paddingY: 1,
                borderBottom: '1px solid',
                borderColor: 'divider',
                fontFamily: 'monospace',
              }}
            >
              <Typography color="text.secondary">{event.timestampMiddleware}</Typography>
              <Typography fontWeight="bold">{event.eventType}</Typography>
              <Typography>{event.content}</Typography>
            </Box>
          ))}

          {events.length === 0 && (
            <Typography textAlign="center">No events found.</Typography>
          )}
        </Paper>
      </Container>
    </>
  );
}

export default Events;