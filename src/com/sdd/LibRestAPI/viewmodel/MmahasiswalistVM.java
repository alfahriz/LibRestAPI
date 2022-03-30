package com.sdd.LibRestAPI.viewmodel;

import java.net.ConnectException;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.Validator;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WebApps;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;

import com.sdd.LibRestAPI.domain.Mmahasiswa;
import com.sdd.LibRestAPI.handler.RespHandler;
import com.sdd.LibRestAPI.pojo.ReqAll;
import com.sdd.LibRestAPI.pojo.ReqData;
import com.sdd.LibRestAPI.pojo.RespAll;
import com.sdd.LibRestAPI.pojo.RespAll2;

public class MmahasiswalistVM {
	private Mmahasiswa objmhsw;
	private List<Mmahasiswa> objmhswlist;
	private boolean isInsert;

	private String npm, namaMahasiswa, email, alamat, jenisKelamin;

	@Wire
	private Listbox listbox;
	@Wire
	private Button btnSave, btnCancel, btnDelete;
	@Wire
	private Groupbox gbform;
	@Wire
	private Caption capt;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		doReset();

		if (listbox != null) {
			listbox.setItemRenderer(new ListitemRenderer<Mmahasiswa>() {
				@Override
				public void render(Listitem item, Mmahasiswa data, int index) throws Exception {
					Listcell cell = new Listcell(String.valueOf(index + 1));
					item.appendChild(cell);
					cell = new Listcell(data.getNpm());
					item.appendChild(cell);
					cell = new Listcell(data.getNamaMahasiswa());
					item.appendChild(cell);
					cell = new Listcell(data.getEmail());
					item.appendChild(cell);
					cell = new Listcell(data.getAlamat());
					item.appendChild(cell);
					cell = new Listcell(data.getJenisKelamin());
					item.appendChild(cell);
				}
			});
		}
		
		listbox.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				isInsert = false;
				btnSave.setIconSclass("z-icon-edit");
				btnSave.setLabel("Update");
				btnDelete.setDisabled(false);
				gbform.setOpen(true);
				btnDelete.setVisible(true);
				doForm();
			}
		});
	}

	public void doRefresh() {
		try {
			String url = "http://10.12.4.166:8083/api/Mahasiswa";
			RespAll rsp = RespHandler.getObject(url);
			if (rsp.getResponseType().equals("CollectData")) {
				ObjectMapper mapper = new ObjectMapper();
				List<Mmahasiswa> objmhswlist = mapper.convertValue(rsp.getResponseBody().getContent(),
						new TypeReference<List<Mmahasiswa>>() {
						});
				listbox.setModel(new ListModelList<>(objmhswlist));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Command
	@NotifyChange("*")
	public void doSave() {
		try {
			String url = "";

			ReqAll<ReqData<Mmahasiswa>> reqall = new ReqAll<>();
			ReqData<Mmahasiswa> reqdata = new ReqData<>();

			reqall.setOperationType("AddData");
			reqall.setOperationDesc("Tambah Data mahasiswa");
			reqdata.getContent().setNpm(npm);
			reqdata.getContent().setNamaMahasiswa(namaMahasiswa);
			reqdata.getContent().setEmail(email);
			reqdata.getContent().setAlamat(alamat);
			reqdata.getContent().setJenisKelamin(jenisKelamin);
			reqdata.getContent().setIsActive("True");
			reqdata.getContent().setCreatedDate(new Date().toString());

			reqall.setRequest(reqdata);

			RespAll2 rsp = new RespAll2();
			if (isInsert) {
				url = "http://10.12.4.166:8083/api/Mahasiswa";
				rsp = RespHandler.postObject(url, objmhsw);
				System.out.println(rsp.getResponseMessage());

				if (rsp.getResponseType().equals("AddData")) {
					Messagebox.show("Mahasiswa '" + objmhsw.getNamaMahasiswa() + "' Berhasil Ditambahkan.",
							WebApps.getCurrent().getAppName(), Messagebox.OK, Messagebox.INFORMATION);
				} else {
					Messagebox.show(rsp.getErrorMessage(), WebApps.getCurrent().getAppName(), Messagebox.OK,
							Messagebox.INFORMATION);
				}
			} else {
				url = "http://10.12.4.166:8083/api/Mahasiswa/update/" + objmhsw.getId();
				System.out.println("url update: " + url);
				rsp = RespHandler.putObject(url, reqall);

				if (rsp.getErrorMessage() == null) {
					Messagebox.show("Mahasiswa '" + reqdata.getContent().getNamaMahasiswa() + "' Berhasil Diperbarui.");
				} else {
					Messagebox.show(rsp.getErrorMessage(), WebApps.getCurrent().getAppName(), Messagebox.OK,
							Messagebox.INFORMATION);
				}
			}
			doReset();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Command
	@NotifyChange("*")
	public void doCancel() {
		doReset();
	}

	@Command
	@NotifyChange("*")
	public void doForm() {
		if (!gbform.isOpen()) {
			capt.setIconSclass("z-icon-arrow-circle-down");
		} else {
			capt.setIconSclass("z-icon-arrow-circle-up");
		}
	}

	@Command
	@NotifyChange("*")
	public void doReset() {
		isInsert = true;
		npm = "";
		namaMahasiswa = "";
		email = "";
		alamat = "";
		jenisKelamin = "";
		objmhsw = new Mmahasiswa();

		doRefresh();
		btnDelete.setDisabled(true);
		btnDelete.setVisible(false);
		btnSave.setIconSclass("z-icon-save");
		btnSave.setLabel("Save");
		btnSave.setClass("btn-primary");
		gbform.setOpen(false);
		doForm();
		doRefresh();
	}

	@Command
	@NotifyChange("*")
	public void doAddnew() {
		doReset();
		gbform.setOpen(true);
		doForm();
	}

	@Command
	@NotifyChange("objmhsw")
	public void doDelete() {
		try {
			Messagebox.show("Anda yakin ingin menghapus mahasiswa ini?", "Confirm Dialog",
					Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new EventListener<Event>() {
						@Override
						public void onEvent(Event event) throws Exception {
							if (event.getName().equals("onOK")) {
								String url = "http://10.12.4.166:8083/api/Mahasiswa/delete/" + objmhsw.getId();
								RespAll rsp = RespHandler.delObject(url, objmhsw);
								if (rsp.getErrorCode() == 201) {
									Messagebox.show("Mahasiswa '" + objmhsw.getNamaMahasiswa() + "' Berhasil Dihapus.",
											WebApps.getCurrent().getAppName(), Messagebox.OK, Messagebox.INFORMATION);
									doReset();
									BindUtils.postNotifyChange(null, null, MmahasiswalistVM.this, "*");
								} else {
									Messagebox.show(rsp.getErrorMessage(), WebApps.getCurrent().getAppName(),
											Messagebox.OK, Messagebox.INFORMATION);
								}
							}
						}
					});
		} catch (Exception e) {
			if (e.getCause() instanceof ConnectException) {
				Messagebox.show(e.getMessage(), WebApps.getCurrent().getAppName(), Messagebox.OK, Messagebox.ERROR);
			}
			e.printStackTrace();
		}
	}

	public Validator getValidator() {
		return new AbstractValidator() {
			@Override
			public void validate(ValidationContext ctx) {
				String npm = (String) ctx.getProperties("npm")[0].getValue();
				String namaMahasiswa = (String) ctx.getProperties("namaMahasiswa")[0].getValue();
				String email = (String) ctx.getProperties("email")[0].getValue();
				String alamat = (String) ctx.getProperties("alamat")[0].getValue();
				String jenisKelamin = (String) ctx.getProperties("jenisKelamin")[0].getValue();

				if (npm == null || "".equals(npm.trim()))
					this.addInvalidMessage(ctx, "npm", "Kolom NPM tidak boleh kosong.");
				if (namaMahasiswa == null || "".equals(namaMahasiswa.trim()))
					this.addInvalidMessage(ctx, "namaMahasiswa", "Kolom Nama Mahasiswa tidak boleh kosong.");
				if (email == null || "".equals(email.trim()))
					this.addInvalidMessage(ctx, "email", "Kolom Email tidak boleh kosong.");
				if (alamat == null || "".equals(alamat.trim()))
					this.addInvalidMessage(ctx, "alamat", "Kolom Alamat tidak boleh kosong.");
				if (jenisKelamin == null || "".equals(jenisKelamin.trim()))
					this.addInvalidMessage(ctx, "jenisKelamin", "Kolom Jenis Kelamin tidak boleh kosong.");
			}
		};
	}

	public Mmahasiswa getObjmhsw() {
		return objmhsw;
	}

	public void setObjmhsw(Mmahasiswa objmhsw) {
		this.objmhsw = objmhsw;
	}

	public List<Mmahasiswa> getObjmhswlist() {
		return objmhswlist;
	}

	public void setObjmhswlist(List<Mmahasiswa> objmhswlist) {
		this.objmhswlist = objmhswlist;
	}

	public String getNpm() {
		return npm;
	}

	public void setNpm(String npm) {
		this.npm = npm;
	}

	public String getNamaMahasiswa() {
		return namaMahasiswa;
	}

	public void setNamaMahasiswa(String namaMahasiswa) {
		this.namaMahasiswa = namaMahasiswa;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAlamat() {
		return alamat;
	}

	public void setAlamat(String alamat) {
		this.alamat = alamat;
	}

	public String getJenisKelamin() {
		return jenisKelamin;
	}

	public void setJenisKelamin(String jenisKelamin) {
		this.jenisKelamin = jenisKelamin;
	}
}