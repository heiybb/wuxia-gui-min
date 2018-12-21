package application.controller;

import application.model.BangPaiMember;
import com.jfoenix.controls.*;
import com.jfoenix.controls.cells.editors.IntegerTextFieldEditorBuilder;
import com.jfoenix.controls.cells.editors.TextFieldEditorBuilder;
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TreeTableColumn;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.function.Function;
import java.util.logging.Logger;

import static application.Main.pStage;
import static application.controller.DateProcessor.getCurrentWeekDate;
import static application.controller.DateProcessor.updateRangeDate;

/**
 * @author heiybb
 */
public class MainWindowController implements DKPFileSet {
    public JFXTreeTableView<BangPaiMember> dkpTable;
    /**
     * TreeTableColumnSection
     */
    public JFXTreeTableColumn<BangPaiMember, String> idColumn;
    public JFXTreeTableColumn<BangPaiMember, Integer> bpwrColumn;
    public JFXTreeTableColumn<BangPaiMember, Integer> bpzxColumn;
    public JFXTreeTableColumn<BangPaiMember, Integer> xzhhColumn;
    public JFXTreeTableColumn<BangPaiMember, Integer> kfzcColumn;
    public JFXTreeTableColumn<BangPaiMember, Integer> lueduoColumn;
    public JFXTreeTableColumn<BangPaiMember, Integer> zhenfengColumn;
    public JFXTreeTableColumn<BangPaiMember, Integer> zijinColumn;
    public JFXTreeTableColumn<BangPaiMember, Integer> yushiColumn;
    public JFXTreeTableColumn<BangPaiMember, Integer> dkpColumn;
    public JFXTreeTableColumn<BangPaiMember, Integer> rewardColumn;

    ObservableList<BangPaiMember> BangPaiMemberList = FXCollections.observableArrayList();

    private FileChooser fileChooser = new FileChooser();
    private Stage mainWindowStage;
    private File dkpFile;

    @FXML
    private void initialize() {
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("TXT", "*.txt")
        );
        initTableColumn();
        dkpTable.setRoot(new RecursiveTreeItem<>(BangPaiMemberList, RecursiveTreeObject::getChildren));
        dkpTable.setShowRoot(false);
        dkpTable.setEditable(true);

    }

    @FXML
    private void jiLiFileExport() {
        Platform.runLater(() -> {
            if (BangPaiMemberList.size() != 0) {
                fileChooser.setInitialFileName(JILIFILE);
                File file = fileChooser.showSaveDialog(pStage);
                if (file != null) {
                    final String[] sum = {JiLiTemplate};
                    BangPaiMemberList.forEach(bangPaiMember -> {
                        if (bangPaiMember.getReward() == 8) {
                            sum[0] += "8/8\t" + bangPaiMember.getId() + "\t95\tX\t0\t0\t0\t0\t8" + "\n";
                        } else if (bangPaiMember.getReward() == 7) {
                            sum[0] += "7/8\t" + bangPaiMember.getId() + "\t95\tX\t0\t0\t0\t0\t7" + "\n";
                        } else if (bangPaiMember.getReward() == 6) {
                            sum[0] += "6/8\t" + bangPaiMember.getId() + "\t95\tX\t0\t0\t0\t0\t6" + "\n";
                        } else if (bangPaiMember.getReward() == 5) {
                            sum[0] += "5/8\t" + bangPaiMember.getId() + "\t95\tX\t0\t0\t0\t0\t5" + "\n";
                        } else if (bangPaiMember.getReward() == 4) {
                            sum[0] += "4/8\t" + bangPaiMember.getId() + "\t95\tX\t0\t0\t0\t0\t4" + "\n";
                        } else if (bangPaiMember.getReward() == 3) {
                            sum[0] += "3/8\t" + bangPaiMember.getId() + "\t95\tX\t0\t0\t0\t0\t3" + "\n";
                        } else if (bangPaiMember.getReward() == 2) {
                            sum[0] += "2/8\t" + bangPaiMember.getId() + "\t95\tX\t0\t0\t0\t0\t2" + "\n";
                        } else if (bangPaiMember.getReward() == 1) {
                            sum[0] += "1/8\t" + bangPaiMember.getId() + "\t95\tX\t0\t0\t0\t0\t1" + "\n";
                        }

                    });
                    bufferWrite(file, sum[0]);
                }
            }
        });
    }

    @FXML
    private void dkpFileSelect() {
        Platform.runLater(() -> {
            fileChooser.setInitialFileName(DKPFILE);
            dkpFile = fileChooser.showOpenDialog(pStage);
            if (dkpFile != null) {
                getCurrentWeekDate();
                updateMemberTable();
            }
        });
    }


    private <T> void setupCellValueFactory(JFXTreeTableColumn<BangPaiMember, T> column, Function<BangPaiMember, ObservableValue<T>> mapper) {
        column.setCellValueFactory((TreeTableColumn.CellDataFeatures<BangPaiMember, T> param) -> {
            if (column.validateValue(param)) {
                return mapper.apply(param.getValue().getValue());
            } else {
                return column.getComputedValue(param);
            }
        });
    }


    private void initTableColumn() {
        setupCellValueFactory(idColumn, BangPaiMember::idProperty);
        setupCellValueFactory(bpwrColumn, member -> member.bpwrProperty().asObject());
        setupCellValueFactory(bpzxColumn, member -> member.bpzxProperty().asObject());
        setupCellValueFactory(xzhhColumn, member -> member.xzhhProperty().asObject());
        setupCellValueFactory(kfzcColumn, member -> member.kfzcProperty().asObject());
        setupCellValueFactory(lueduoColumn, member -> member.lueduoProperty().asObject());
        setupCellValueFactory(zhenfengColumn, member -> member.zhenfengProperty().asObject());
        setupCellValueFactory(zijinColumn, member -> member.zijinProperty().asObject());
        setupCellValueFactory(yushiColumn, member -> member.yushiProperty().asObject());
        setupCellValueFactory(dkpColumn, member -> member.dkpProperty().asObject());
        setupCellValueFactory(rewardColumn, member -> member.rewardProperty().asObject());
        //set editable
        rewardColumn.setCellFactory((TreeTableColumn<BangPaiMember, Integer> param) -> new GenericEditableTreeTableCell<>(
                new IntegerTextFieldEditorBuilder()));
        rewardColumn.setOnEditCommit((TreeTableColumn.CellEditEvent<BangPaiMember, Integer> t) -> {
            (t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue()).rewardProperty().
                    set(t.getNewValue());
        });

    }

    private void updateMemberTable() {
        ArrayList<BangPaiMember> memberDataBase = new ArrayList<>();

        DKPFileProcessor dkpFileProcessor = new DKPFileProcessor();
        try {
            if (dkpFile != null) {
                memberDataBase = dkpFileProcessor.fileToRecordList(dkpFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        BangPaiMemberList.clear();
        BangPaiMemberList.addAll(memberDataBase);
    }

    private void bufferWrite(File file, String content) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file.getPath()));
            bw.write(content);
            bw.close();
        } catch (IOException io) {
            Logger.getLogger(this.getClass().getName() + "Buffer Write IO Exception");
        }
    }
}
