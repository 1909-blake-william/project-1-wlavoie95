package com.revature.models;

import java.sql.Timestamp;

public class Reimbursement {
	private int id;
	private int authorId;
	private int typeId;
	private Timestamp submitted;
	private double amount;
	private String description;
	private String ticketType;
	private String authorFirstName;
	private String authorLastName;
	private String authorEmail;
	private Timestamp resolved;
	private int resolverId;
	private String resolverFirstName;
	private String resolverLastName;
	private String resolverEmail;
	private String statusType;
	
	
	
	public Reimbursement() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Reimbursement(double amount, String description, int authorId, int typeId) {
		super();
		this.id = 0;
		this.submitted = null;
		this.amount = amount;
		this.authorId = authorId;
		this.typeId = typeId;
		this.description = description;
	}
	
	public Reimbursement(int id, int authorId, int typeId, Timestamp submitted, double amount, String description,
			String ticketType, String authorFirstName, String authorLastName, String authorEmail, Timestamp resolved,
			int resolverId, String resolverFirstName, String resolverLastName, String resolverEmail,
			String statusType) {
		super();
		this.id = id;
		this.authorId = authorId;
		this.typeId = typeId;
		this.submitted = submitted;
		this.amount = amount;
		this.description = description;
		this.ticketType = ticketType;
		this.authorFirstName = authorFirstName;
		this.authorLastName = authorLastName;
		this.authorEmail = authorEmail;
		this.resolved = resolved;
		this.resolverId = resolverId;
		this.resolverFirstName = resolverFirstName;
		this.resolverLastName = resolverLastName;
		this.resolverEmail = resolverEmail;
		this.statusType = statusType;
	}

	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public int getAuthorId() {
		return authorId;
	}



	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}



	public int getTypeId() {
		return typeId;
	}



	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}



	public Timestamp getSubmitted() {
		return submitted;
	}



	public void setSubmitted(Timestamp submitted) {
		this.submitted = submitted;
	}



	public double getAmount() {
		return amount;
	}



	public void setAmount(double amount) {
		this.amount = amount;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public String getTicketType() {
		return ticketType;
	}



	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}



	public String getAuthorFirstName() {
		return authorFirstName;
	}



	public void setAuthorFirstName(String authorFirstName) {
		this.authorFirstName = authorFirstName;
	}



	public String getAuthorLastName() {
		return authorLastName;
	}



	public void setAuthorLastName(String authorLastName) {
		this.authorLastName = authorLastName;
	}



	public String getAuthorEmail() {
		return authorEmail;
	}



	public void setAuthorEmail(String authorEmail) {
		this.authorEmail = authorEmail;
	}



	public Timestamp getResolved() {
		return resolved;
	}



	public void setResolved(Timestamp resolved) {
		this.resolved = resolved;
	}



	public int getResolverId() {
		return resolverId;
	}



	public void setResolverId(int resolverId) {
		this.resolverId = resolverId;
	}



	public String getResolverFirstName() {
		return resolverFirstName;
	}



	public void setResolverFirstName(String resolverFirstName) {
		this.resolverFirstName = resolverFirstName;
	}



	public String getResolverLastName() {
		return resolverLastName;
	}



	public void setResolverLastName(String resolverLastName) {
		this.resolverLastName = resolverLastName;
	}



	public String getResolverEmail() {
		return resolverEmail;
	}



	public void setResolverEmail(String resolverEmail) {
		this.resolverEmail = resolverEmail;
	}



	public String getStatusType() {
		return statusType;
	}



	public void setStatusType(String statusType) {
		this.statusType = statusType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((authorEmail == null) ? 0 : authorEmail.hashCode());
		result = prime * result + ((authorFirstName == null) ? 0 : authorFirstName.hashCode());
		result = prime * result + authorId;
		result = prime * result + ((authorLastName == null) ? 0 : authorLastName.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + id;
		result = prime * result + ((resolved == null) ? 0 : resolved.hashCode());
		result = prime * result + ((resolverEmail == null) ? 0 : resolverEmail.hashCode());
		result = prime * result + ((resolverFirstName == null) ? 0 : resolverFirstName.hashCode());
		result = prime * result + resolverId;
		result = prime * result + ((resolverLastName == null) ? 0 : resolverLastName.hashCode());
		result = prime * result + ((statusType == null) ? 0 : statusType.hashCode());
		result = prime * result + ((submitted == null) ? 0 : submitted.hashCode());
		result = prime * result + ((ticketType == null) ? 0 : ticketType.hashCode());
		result = prime * result + typeId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reimbursement other = (Reimbursement) obj;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		if (authorEmail == null) {
			if (other.authorEmail != null)
				return false;
		} else if (!authorEmail.equals(other.authorEmail))
			return false;
		if (authorFirstName == null) {
			if (other.authorFirstName != null)
				return false;
		} else if (!authorFirstName.equals(other.authorFirstName))
			return false;
		if (authorId != other.authorId)
			return false;
		if (authorLastName == null) {
			if (other.authorLastName != null)
				return false;
		} else if (!authorLastName.equals(other.authorLastName))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (resolved == null) {
			if (other.resolved != null)
				return false;
		} else if (!resolved.equals(other.resolved))
			return false;
		if (resolverEmail == null) {
			if (other.resolverEmail != null)
				return false;
		} else if (!resolverEmail.equals(other.resolverEmail))
			return false;
		if (resolverFirstName == null) {
			if (other.resolverFirstName != null)
				return false;
		} else if (!resolverFirstName.equals(other.resolverFirstName))
			return false;
		if (resolverId != other.resolverId)
			return false;
		if (resolverLastName == null) {
			if (other.resolverLastName != null)
				return false;
		} else if (!resolverLastName.equals(other.resolverLastName))
			return false;
		if (statusType == null) {
			if (other.statusType != null)
				return false;
		} else if (!statusType.equals(other.statusType))
			return false;
		if (submitted == null) {
			if (other.submitted != null)
				return false;
		} else if (!submitted.equals(other.submitted))
			return false;
		if (ticketType == null) {
			if (other.ticketType != null)
				return false;
		} else if (!ticketType.equals(other.ticketType))
			return false;
		if (typeId != other.typeId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Reimbursement [id=" + id + ", authorId=" + authorId + ", typeId=" + typeId + ", submitted=" + submitted
				+ ", amount=" + amount + ", description=" + description + ", ticketType=" + ticketType
				+ ", authorFirstName=" + authorFirstName + ", authorLastName=" + authorLastName + ", authorEmail="
				+ authorEmail + ", resolved=" + resolved + ", resolverId=" + resolverId + ", resolverFirstName="
				+ resolverFirstName + ", resolverLastName=" + resolverLastName + ", resolverEmail=" + resolverEmail
				+ ", statusType=" + statusType + "]";
	}

}
