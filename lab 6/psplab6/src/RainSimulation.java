import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RainSimulation extends JFrame {
    private Cloud cloud;
    private WaterTank waterTank;
    private JButton startButton;
    private JButton stopButton;

    public RainSimulation() {
        setTitle("Дождь из тучи");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        cloud = new Cloud();
        waterTank = new WaterTank();

        startButton = new JButton("Старт");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cloud.startRain();
            }
        });

        stopButton = new JButton("Стоп");
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cloud.stopRain();
                waterTank.collectWater();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);

        add(cloud, BorderLayout.NORTH);
        add(waterTank, BorderLayout.SOUTH);
        add(buttonPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RainSimulation simulation = new RainSimulation();
            simulation.setVisible(true);
        });
    }

    public class Cloud extends JPanel {
        private boolean isRaining = false;

        public void startRain() {
            isRaining = true;
            Thread rainThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (isRaining) {
                        repaint();
                        try {
                            Thread.sleep(100); // Задержка для анимации дождя
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            rainThread.start();
        }

        public void stopRain() {
            isRaining = false;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int cloudSize = isRaining ? 50 : 100;
            g.setColor(Color.GRAY);
            g.fillOval(50, 50, cloudSize, cloudSize);
        }
    }

    public class WaterTank extends JPanel {
        private int waterLevel = 0;

        public void collectWater() {
            Thread waterThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 10; i++) {
                        waterLevel += 10;
                        repaint();
                        try {
                            Thread.sleep(500); // Задержка для анимации накопления воды
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            waterThread.start();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.BLUE);
            g.fillRect(100, 50, 200, waterLevel);
        }
    }
}
