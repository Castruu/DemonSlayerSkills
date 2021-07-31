package dslayer.draxy.events.swskill;

import dslayer.draxy.DSMain;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

public class MultipleX implements ISwordSkills {
    @Override
    public void spawnSkill(Player player, double damage, Effect effect, int id, int data) {
            Location location = player.getLocation();
            if(player.isPermissionSet("blackflame")) {
                ineParticle(player, damage, id, data, location);
                return;
            }
            Bukkit.getScheduler().runTaskAsynchronously(DSMain.getInstance(), () -> {
                for(int i = 0; i <= 4; i++) {
                    double radius = 1 + Math.random() * (2 - 1 + 1);
                    final double  random = Math.random();
                    Bukkit.getScheduler().runTaskAsynchronously(DSMain.getInstance(), () -> {
                                int speed = 0;
                                for (double y = 2; y >= -2; y -= 0.05) {
                                    if (random <= 0.125) {
                                        double x = (location.getX() + radius * Math.cos(y));
                                        double y1 = (location.getY() + radius * Math.sin(y));
                                        double z = (location.getZ() + y);
                                        Location particle = new Location(player.getWorld(), x, y1 + 1.5, z);
                                        if(effect == Effect.COLOURED_DUST) {
                                            player.getWorld().spigot().playEffect(particle, effect, id, 1, 255/255F, 240/255F, 0/255F, 1, 0, 64);
                                        } else
                                            player.getWorld().spigot().playEffect(particle, effect, id, data, 0, 0, 0, speed, 10, 10);
                                         try {
                                            TimeUnit.MILLISECONDS.sleep(2);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    if (random > 0.125 && random <= 0.250) {
                                        double x = (location.getX() - radius * Math.cos(y));
                                        double y1 = (location.getY() + radius * Math.sin(y));
                                        double z = (location.getZ() + y);
                                        Location particle = new Location(player.getWorld(), x, y1 + 1.5, z);
                                        if(effect == Effect.COLOURED_DUST) {
                                            player.getWorld().spigot().playEffect(particle, effect, id, 1, 255/255F, 240/255F, 0/255F, 1, 0, 64);
                                         } else
                                            player.getWorld().spigot().playEffect(particle, effect, id, data, 0, 0, 0, speed, 10, 10);
                                         try {
                                            TimeUnit.MILLISECONDS.sleep(2);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    if (random > .250 && random <= 0.375) {
                                        double z = (location.getZ() - radius * Math.cos(y));
                                        double y1 = (location.getY() + radius * Math.sin(y));
                                        double x = (location.getX() + y);
                                        Location particle = new Location(player.getWorld(), x, y1 + 1.5, z);
                                        if(effect == Effect.COLOURED_DUST) {
                                            player.getWorld().spigot().playEffect(particle, effect, id, 1, 255/255F, 240/255F, 0/255F, 1, 0, 64);
                                         } else
                                            player.getWorld().spigot().playEffect(particle, effect, id, data, 0, 0, 0, speed, 10, 10);
                                        try {
                                            TimeUnit.MILLISECONDS.sleep(2);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }


                                    if (random > .375 && random <= 0.500) {
                                        double z = (location.getZ() + radius * Math.cos(y));
                                        double y1 = (location.getY() + radius * Math.sin(y));
                                        double x = (location.getX() + y);
                                        Location particle = new Location(player.getWorld(), x, y1 + 1.5, z);
                                        if(effect == Effect.COLOURED_DUST) {
                                            player.getWorld().spigot().playEffect(particle, effect, id, 1, 255/255F, 240/255F, 0/255F, 1, 0, 64);
                                         } else
                                            player.getWorld().spigot().playEffect(particle, effect, id, data, 0, 0, 0, speed, 10, 10);
                                        try {
                                            TimeUnit.MILLISECONDS.sleep(2);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }


                                    }
                                    if (random > 0.500 && random <= 0.625) {
                                        double x = (location.getX() + radius * Math.cos(y));
                                        double y1 = (location.getY() + radius * Math.sin(y));
                                        double z = (location.getZ() - y);
                                        Location particle = new Location(player.getWorld(), x, y1 + 1.5, z);
                                        if(effect == Effect.COLOURED_DUST) {
                                            player.getWorld().spigot().playEffect(particle, effect, id, 1, 255/255F, 240/255F, 0/255F, 1, 0, 64);
                                         } else
                                            player.getWorld().spigot().playEffect(particle, effect, id, data, 0, 0, 0, speed, 10, 10);
                                        try {
                                            TimeUnit.MILLISECONDS.sleep(2);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }


                                    }
                                    if (random > 0.625 && random <= 0.750) {
                                        double z = (location.getZ() + radius * Math.cos(y));
                                        double y1 = (location.getY() + radius * Math.sin(y));
                                        double x = (location.getX() - y);
                                        Location particle = new Location(player.getWorld(), x, y1 + 1.5, z);
                                        if(effect == Effect.COLOURED_DUST) {
                                            player.getWorld().spigot().playEffect(particle, effect, id, 1, 255/255F, 240/255F, 0/255F, 1, 0, 64);
                                        } else
                                            player.getWorld().spigot().playEffect(particle, effect, id, data, 0, 0, 0, speed, 10, 10);
                                        try {
                                            TimeUnit.MILLISECONDS.sleep(2);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }


                                    }
                                    if (random > 0.750 && random <= 0.875) {
                                        double z = (location.getZ() - radius * Math.cos(y));
                                        double y1 = (location.getY() + radius * Math.sin(y));
                                        double x = (location.getX() - y);
                                        Location particle = new Location(player.getWorld(), x, y1 + 1.5, z);
                                        if(effect == Effect.COLOURED_DUST) {
                                            player.getWorld().spigot().playEffect(particle, effect, id, 1, 255/255F, 240/255F, 0/255F, 1, 0, 64);
                                        } else
                                            player.getWorld().spigot().playEffect(particle, effect, id, data, 0, 0, 0, speed, 10, 10);

                                        try {
                                            TimeUnit.MILLISECONDS.sleep(2);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    if (random > 0.875 && random <= 1) {
                                        double x = (location.getX() - radius * Math.cos(y));
                                        double y1 = (location.getY() + radius * Math.sin(y));
                                        double z = (location.getZ() - y);
                                        Location particle = new Location(player.getWorld(), x, y1 + 1.5, z);
                                        if(effect == Effect.COLOURED_DUST) {
                                            player.getWorld().spigot().playEffect(particle, effect, id, 1, 255/255F, 240/255F, 0/255F, 1, 0, 64);
                                           } else
                                            player.getWorld().spigot().playEffect(particle, effect, id, data, 0, 0, 0, speed, 10, 10);

                                        try {
                                            TimeUnit.MILLISECONDS.sleep(2);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                    this.applyDamage(player, 3, damage);
                                }
                            }
                    );
                    try {
                        TimeUnit.MILLISECONDS.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });


    }

    private void ineParticle(Player player, double damage, int id, int data, Location location) {
        Bukkit.getScheduler().runTaskAsynchronously(DSMain.getInstance(), () -> {
            for(int i = 0; i <= 4; i++) {
                double radius = 1 + Math.random() * (2 - 1 + 1);
                final double  random = Math.random();
                Bukkit.getScheduler().runTaskAsynchronously(DSMain.getInstance(), () -> {
                            int speed = 0;
                            for (double y = 2; y >= -2; y -= 0.05) {
                                if (random <= 0.125) {
                                    double x = (location.getX() + radius * Math.cos(y));
                                    double y1 = (location.getY() + radius * Math.sin(y));
                                    double z = (location.getZ() + y);
                                    Location particle = new Location(player.getWorld(), x, y1 + 1.5, z);
                                      player.getWorld().spigot().playEffect(particle.clone().add(0, 0.5, 0),Effect.WITCH_MAGIC, id, 1, 0, 0, 0, 1, 5, 64);
                                      player.getWorld().spigot().playEffect(particle, Effect.FLAME, id, data, 0, 0, 0, 0, 3, 3);
                                    try {
                                        TimeUnit.MILLISECONDS.sleep(2);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                                if (random > 0.125 && random <= 0.250) {
                                    double x = (location.getX() - radius * Math.cos(y));
                                    double y1 = (location.getY() + radius * Math.sin(y));
                                    double z = (location.getZ() + y);
                                    Location particle = new Location(player.getWorld(), x, y1 + 1.5, z);

                                        player.getWorld().spigot().playEffect(particle.clone().add(0, 0.5, 0), Effect.WITCH_MAGIC, id, 1, 1/255F, 1/255F, 1/255F, 1, 5, 64);
                                        player.getWorld().spigot().playEffect(particle, Effect.FLAME, id, data, 0, 0, 0, 0, 3, 3);
                                    try {
                                        TimeUnit.MILLISECONDS.sleep(2);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                                if (random > .250 && random <= 0.375) {
                                    double z = (location.getZ() - radius * Math.cos(y));
                                    double y1 = (location.getY() + radius * Math.sin(y));
                                    double x = (location.getX() + y);
                                    Location particle = new Location(player.getWorld(), x, y1 + 1.5, z);

                                        player.getWorld().spigot().playEffect(particle.clone().add(0, 0.5, 0), Effect.WITCH_MAGIC, id, 1, 1/255F, 1/255F, 1/255F, 1, 5, 64);
                                        player.getWorld().spigot().playEffect(particle, Effect.FLAME, id, data, 0, 0, 0, 0, 3, 3);

                                    try {
                                        TimeUnit.MILLISECONDS.sleep(2);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }


                                if (random > .375 && random <= 0.500) {
                                    double z = (location.getZ() + radius * Math.cos(y));
                                    double y1 = (location.getY() + radius * Math.sin(y));
                                    double x = (location.getX() + y);
                                    Location particle = new Location(player.getWorld(), x, y1 + 1.5, z);

                                        player.getWorld().spigot().playEffect(particle.clone().add(0, 0.5, 0), Effect.WITCH_MAGIC, id, 1, 1/255F, 1/255F, 1/255F, 1, 5, 64);
                                        player.getWorld().spigot().playEffect(particle, Effect.FLAME, id, data, 0, 0, 0, 0, 3, 3);
                                    try {
                                        TimeUnit.MILLISECONDS.sleep(2);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }


                                }
                                if (random > 0.500 && random <= 0.625) {
                                    double x = (location.getX() + radius * Math.cos(y));
                                    double y1 = (location.getY() + radius * Math.sin(y));
                                    double z = (location.getZ() - y);
                                    Location particle = new Location(player.getWorld(), x, y1 + 1.5, z);
                                        player.getWorld().spigot().playEffect(particle.clone().add(0, 0.5, 0), Effect.WITCH_MAGIC, id, 1, 1/255F, 1/255F, 1/255F, 1, 5, 64);
                                        player.getWorld().spigot().playEffect(particle, Effect.FLAME, id, data, 0, 0, 0, 0, 3, 3);
                                    try {
                                        TimeUnit.MILLISECONDS.sleep(2);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }


                                }
                                if (random > 0.625 && random <= 0.750) {
                                    double z = (location.getZ() + radius * Math.cos(y));
                                    double y1 = (location.getY() + radius * Math.sin(y));
                                    double x = (location.getX() - y);
                                    Location particle = new Location(player.getWorld(), x, y1 + 1.5, z);
                                        player.getWorld().spigot().playEffect(particle.clone().add(0, 0.5, 0), Effect.WITCH_MAGIC, id, 1, 1/255F, 1/255F, 1/255F, 1, 5, 64);
                                        player.getWorld().spigot().playEffect(particle, Effect.FLAME, id, data, 0, 0, 0, 0, 3, 3);
                                    try {
                                        TimeUnit.MILLISECONDS.sleep(2);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }


                                }
                                if (random > 0.750 && random <= 0.875) {
                                    double z = (location.getZ() - radius * Math.cos(y));
                                    double y1 = (location.getY() + radius * Math.sin(y));
                                    double x = (location.getX() - y);
                                    Location particle = new Location(player.getWorld(), x, y1 + 1.5, z);
                                        player.getWorld().spigot().playEffect(particle.clone().add(0, 0.5, 0), Effect.WITCH_MAGIC, id, 1, 1/255F, 1/255F, 1/255F, 1, 5, 64);
                                        player.getWorld().spigot().playEffect(particle, Effect.FLAME, id, data, 0, 0, 0, 0, 3, 3);
                                    try {
                                        TimeUnit.MILLISECONDS.sleep(2);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                                if (random > 0.875 && random <= 1) {
                                    double x = (location.getX() - radius * Math.cos(y));
                                    double y1 = (location.getY() + radius * Math.sin(y));
                                    double z = (location.getZ() - y);
                                    Location particle = new Location(player.getWorld(), x, y1 + 1.5, z);
                                        player.getWorld().spigot().playEffect(particle.clone().add(0, 0.5, 0), Effect.WITCH_MAGIC, id, 1, 1/255F, 1/255F, 1/255F, 1, 5, 64);
                                        player.getWorld().spigot().playEffect(particle, Effect.FLAME, id, data, 0, 0, 0, 0, 3, 3);
                                    try {
                                        TimeUnit.MILLISECONDS.sleep(2);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }

                                }
                                this.applyDamage(player, 3, damage);
                            }
                        }
                );
                try {
                    TimeUnit.MILLISECONDS.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
