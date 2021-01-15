package com.narola.core.task.utility;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class DailyTaskStatusEmailTemplate {

	public static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMMM, yyyy");

	private Document htmlDocument = null;
	private String projectName;
	private List<String> listOfCompletedTask;
	private List<String> listOfInPorgressTask;
	private List<String> listOfRemainingTask;

	public DailyTaskStatusEmailTemplate(File file, InputStream inputStream) throws IOException {
		htmlDocument = Jsoup.parse(file, "UTF-8");
	}

	public String getProjectName() {
		return projectName;
	}

	public DailyTaskStatusEmailTemplate setProjectName(String projectName) {
		this.projectName = projectName;
		return this;
	}

	public List<String> getListOfCompletedTask() {
		return listOfCompletedTask;
	}

	public DailyTaskStatusEmailTemplate setListOfCompletedTask(List<String> listOfCompletedTask) {
		this.listOfCompletedTask = listOfCompletedTask;
		return this;
	}

	public List<String> getListOfInPorgressTask() {
		return listOfInPorgressTask;
	}

	public DailyTaskStatusEmailTemplate setListOfInPorgressTask(List<String> listOfInPorgressTask) {
		this.listOfInPorgressTask = listOfInPorgressTask;
		return this;
	}

	public List<String> getListOfRemainingTask() {
		return listOfRemainingTask;
	}

	public DailyTaskStatusEmailTemplate setListOfRemainingTask(List<String> listOfRemainingTask) {
		this.listOfRemainingTask = listOfRemainingTask;
		return this;
	}

	public String generateHtml() {
		// 1.
		htmlDocument.body().getElementsByClass("client_name").first().child(0).appendText("Team");

		// 2.
		Element updateMsgElement = htmlDocument.body().getElementsByClass("update_msg").first().child(0);
		// @formatter:off	
		String updateMsgElementText = updateMsgElement.text()
														.replace("{projectName}", getProjectName())
														.replace("{date}", dateFormatter.format(LocalDate.now()));
		updateMsgElement.text(updateMsgElementText);				
		// @formatter:on

		// 3.
		Element listDoneElement = htmlDocument.body().getElementsByClass("list_done").first();
		List<String> doneTask = getListOfCompletedTask();
		if (doneTask == null || doneTask.isEmpty()) {
			listDoneElement.remove();
			// remove 'review_note' section as well
			htmlDocument.body().getElementsByClass("review_note").first().remove();
		} else {
			Element olTagForDoneTask = listDoneElement.getElementsByTag("ol").first();
			for (String taskDes : doneTask) {
				olTagForDoneTask.appendElement("li").text(taskDes + " ").appendElement("b").text("[Done]");
			}
		}

		// 4.
		Element listProgressElement = htmlDocument.body().getElementsByClass("list_progress").first();
		List<String> inProgressTask = getListOfInPorgressTask();
		if (inProgressTask == null || inProgressTask.isEmpty()) {
			listProgressElement.remove();
		} else {
			Element olTagForDoneTask = listProgressElement.getElementsByTag("ol").first();
			for (String taskDes : inProgressTask) {
				olTagForDoneTask.appendElement("li").text(taskDes);
			}
		}

		// 5.
		Element listRemainingElement = htmlDocument.body().getElementsByClass("list_remaining").first();
		List<String> remainingTask = getListOfRemainingTask();
		if (remainingTask == null || remainingTask.isEmpty()) {
			listRemainingElement.remove();
		} else {
			Element olTagForDoneTask = listRemainingElement.getElementsByTag("ol").first();
			for (String taskDes : remainingTask) {
				olTagForDoneTask.appendElement("li").text(taskDes);
			}
		}
		return htmlDocument.html();
	}

	public static List<String> getTestDoneList() {
//		return null;
		return Arrays.asList(
				"First task First task First task First task First task First task First task First task First task First task First task ",
				"Second task", "Third task");
	}

	public static List<String> geTestInProgressList() {
//		return null;
		return Arrays.asList(
				"First task First task First task First task First task First task First task First task First task First task First task ",
				"Second task", "Third task");
	}

	public static List<String> getTestRemainingList() {
//		return null;
		return Arrays.asList(
				"First task First task First task First task First task First task First task First task First task First task First task ",
				"Second task", "Third task");
	}

	public static void main(String[] args) {
		try {
			Document htmlDocument = Jsoup.parse(new File("E:\\Prashant\\html_report.html"), "UTF-8");

			// 1.
			htmlDocument.body().getElementsByClass("client_name").first().child(0).appendText("Team");

			// 2.
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM, yyyy");
			Element updateMsgElement = htmlDocument.body().getElementsByClass("update_msg").first().child(0);
			// @formatter:off			
						String updateMsgElementText = updateMsgElement.text()
																		.replace("{projectName}", "Task Management service")
																		.replace("{date}", formatter.format(LocalDate.now()));			
						updateMsgElement.text(updateMsgElementText);
						// @formatter:on

			// 3.
			Element listDoneElement = htmlDocument.body().getElementsByClass("list_done").first();
			List<String> doneTask = getTestDoneList();
			if (doneTask == null || doneTask.isEmpty()) {
				listDoneElement.remove();
				htmlDocument.body().getElementsByClass("review_note").first().remove();
			} else {
				Element olTagForDoneTask = listDoneElement.getElementsByTag("ol").first();
				for (String taskDes : doneTask) {
					olTagForDoneTask.appendElement("li").text(taskDes + " ").appendElement("b").text("[Done]");
				}
			}

			// 4.
			Element listProgressElement = htmlDocument.body().getElementsByClass("list_progress").first();
			List<String> inProgressTask = geTestInProgressList();
			if (inProgressTask == null || inProgressTask.isEmpty()) {
				listProgressElement.remove();
			} else {
				Element olTagForDoneTask = listProgressElement.getElementsByTag("ol").first();
				for (String taskDes : inProgressTask) {
					olTagForDoneTask.appendElement("li").text(taskDes);
				}
			}

			// 5.
			Element listRemainingElement = htmlDocument.body().getElementsByClass("list_remaining").first();
			List<String> remainingTask = getTestRemainingList();
			if (remainingTask == null || remainingTask.isEmpty()) {
				listRemainingElement.remove();
			} else {
				Element olTagForDoneTask = listRemainingElement.getElementsByTag("ol").first();
				for (String taskDes : remainingTask) {
					olTagForDoneTask.appendElement("li").text(taskDes);
				}
			}
			System.out.println(htmlDocument.body().html());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}