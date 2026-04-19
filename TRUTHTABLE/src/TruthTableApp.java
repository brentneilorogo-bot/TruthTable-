import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TruthTableApp {

    // Logical implication (p → q)
    public static boolean implies(boolean p, boolean q) {
        return !p || q;
    }

    // Convert boolean to T/F
    public static String toTF(boolean value) {
        return value ? "T" : "F";
    }

    // Custom color renderer
    static class ColorRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(
                JTable table, Object value,
                boolean isSelected, boolean hasFocus,
                int row, int column) {

            Component c = super.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column);

            if (value != null && value.equals("T")) {
                c.setForeground(new Color(0, 150, 0)); // green
            } else {
                c.setForeground(Color.RED);
            }

            setHorizontalAlignment(CENTER);
            return c;
        }
    }

    public static void main(String[] args) {

        // Frame
        JFrame frame = new JFrame("Truth Table Generator");
        frame.setSize(650, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Title
        JLabel title = new JLabel("Truth Table: ¬p V (p → q)", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        frame.add(title, BorderLayout.NORTH);

        // Table setup
        String[] columns = {"p", "q", "¬p", "p→q", "Result"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);

        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.BOLD, 14));

        // Apply color renderer
        ColorRenderer renderer = new ColorRenderer();
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Buttons panel
        JPanel panel = new JPanel();

        JButton generateBtn = new JButton("Generate Table");
        JButton testBtn = new JButton("Test Values");
        JButton clearBtn = new JButton("Clear");

        panel.add(generateBtn);
        panel.add(testBtn);
        panel.add(clearBtn);

        frame.add(panel, BorderLayout.SOUTH);

        // Generate table action
        generateBtn.addActionListener((ActionEvent e) -> {
            model.setRowCount(0); // clear

            boolean[] values = {true, false};

            for (boolean p : values) {
                for (boolean q : values) {

                    boolean notP = !p;
                    boolean pImpliesQ = implies(p, q);
                    boolean result = notP || pImpliesQ;

                    model.addRow(new Object[]{
                            toTF(p),
                            toTF(q),
                            toTF(notP),
                            toTF(pImpliesQ),
                            toTF(result)
                    });
                }
            }
        });

        // Test custom values
        testBtn.addActionListener((ActionEvent e) -> {

            String pInput = JOptionPane.showInputDialog(frame, "Enter p (T/F):");
            String qInput = JOptionPane.showInputDialog(frame, "Enter q (T/F):");

            if (pInput == null || qInput == null) return;

            boolean p = pInput.equalsIgnoreCase("T");
            boolean q = qInput.equalsIgnoreCase("T");

            boolean notP = !p;
            boolean pImpliesQ = implies(p, q);
            boolean result = notP || pImpliesQ;

            JOptionPane.showMessageDialog(frame,
                    "p = " + toTF(p) +
                    "\nq = " + toTF(q) +
                    "\n\n¬p = " + toTF(notP) +
                    "\np → q = " + toTF(pImpliesQ) +
                    "\n\nFinal Result = " + toTF(result),
                    "Result",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });

        // Clear table
        clearBtn.addActionListener((ActionEvent e) -> {
            model.setRowCount(0);
        });

        // Show frame
        frame.setVisible(true);
    }
}
