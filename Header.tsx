import { useState } from "react";

import AppBar from "@mui/material/AppBar";
import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import Container from "@mui/material/Container";
import Drawer from "@mui/material/Drawer";
import Link from "@mui/material/Link";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import MenuIcon from "@mui/icons-material/Menu";

import { Link as RouterLink } from "react-router";

const pages: { name: string; path: string }[] = [
  { name: "イベント一覧", path: "/events" },
  { name: "マイページ", path: "/mypage" },
  { name: "login", path: "/login" },
];

type NavListProps = {
  display?: {
    xs?: string;
    sm?: string;
  };
  onClick?: () => void;
};

function NavList({ display, onClick }: NavListProps): JSX.Element {
  return (
    <Box
      component="nav"
      sx={{
        display,
        overflow: "auto",
        flexDirection: { xs: "column", sm: "row" },
        alignItems: "center",
        justifyContent: { xs: "flex-start", sm: "center" },
        gap: { xs: 2, sm: 3 },
        width: { xs: "100%", sm: "auto" },
        mt: { xs: 4, sm: 0 },
      }}
    >
      {pages.map((page) => (
        <Link
          key={page.path}
          component={RouterLink}
          to={page.path}
          onClick={onClick}
          sx={{
            color: { xs: "primary.main", sm: "white" },
            textDecoration: "none",
            fontWeight: 500,
            whiteSpace: "nowrap",
            display: "block",
            textAlign: "center",
            width: { xs: "100%", sm: "auto" },
          }}
        >
          {page.name}
        </Link>
      ))}
    </Box>
  );
}

function Nav(): JSX.Element {
  const [open, setOpen] = useState(false);

  const toggleDrawer = (newOpen: boolean) => (): void => {
    setOpen(newOpen);
  };

  const closeDrawer = (): void => {
    setOpen(false);
  };

  return (
    <>
      <Button
        variant="text"
        onClick={toggleDrawer(true)}
        sx={{
          color: "white",
          display: { xs: "flex", sm: "none" },
          minWidth: "auto",
          p: 1,
        }}
      >
        <MenuIcon />
      </Button>

      <Drawer
        open={open}
        onClose={toggleDrawer(false)}
        anchor="right"
        sx={{
          display: { xs: "inherit", sm: "none" },
        }}
        slotProps={{
          paper: {
            sx: {
              width: 220,
              display: "flex",
              alignItems: "center",
            },
          },
        }}
      >
        <NavList display={{ xs: "flex", sm: "none" }} onClick={closeDrawer} />
      </Drawer>

      <NavList display={{ xs: "none", sm: "flex" }} />
    </>
  );
}

function Header(): JSX.Element {
  return (
    <AppBar position="static">
      <Container maxWidth={false}>
        <Toolbar disableGutters>
          <Box
            component="nav"
            sx={{
              display: "flex",
              alignItems: "center",
              width: "100%",
              minHeight: 64,
              px: 2,
            }}
          >
            <Typography
              variant="h6"
              component={RouterLink}
              to="/events"
              sx={{
                color: "white",
                textDecoration: "none",
                fontWeight: 700,
                whiteSpace: "nowrap",
              }}
            >
              人材交流アプリ
            </Typography>

            <Box
              sx={{
                ml: "auto",
                display: "flex",
                alignItems: "center",
              }}
            >
              <Nav />
            </Box>
          </Box>
        </Toolbar>
      </Container>
    </AppBar>
  );
}

export default Header;
