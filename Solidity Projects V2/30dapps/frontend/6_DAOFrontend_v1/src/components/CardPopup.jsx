import { Box, IconButton, Paper, Typography } from "@mui/material";
import CloseIcon from "@mui/icons-material/Close";

function CardPopup({ text, onClose }) {
  return (
    <Box
      position="fixed"
      top={0}
      left={0}
      width="100vw"
      height="100vh"
      display="flex"
      justifyContent="center"
      alignItems="center"
      zIndex={1300}
      bgcolor="rgba(0, 0, 0, 0.4)"
      onClick={onClose} // <-- Clicking outside closes popup
    >
      <Paper
        elevation={6}
        onClick={(e) => e.stopPropagation()} // <-- Prevent close when clicking inside card
        sx={{
          position: "relative",
          p: 4,
          borderRadius: 4,
          maxWidth: 400,
          width: "90%",
          bgcolor: (theme) => theme.palette.background.cardpopup,
          backdropFilter: "blur(10px)",
          color: "text.primary",
          textAlign: "center",
        }}
      >
        <IconButton
          onClick={onClose}
          sx={{ position: "absolute", top: 8, right: 8, color: "white" }}
        >
          <CloseIcon />
        </IconButton>

        <Typography variant="h6" fontWeight="bold">
          {text}
        </Typography>
      </Paper>
    </Box>
  );
}

export default CardPopup;