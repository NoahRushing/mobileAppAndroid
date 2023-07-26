package edu.ucsc.cse118.assignment3

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.ucsc.cse118.assignment3.data.Channel
import edu.ucsc.cse118.assignment3.data.Member
import edu.ucsc.cse118.assignment3.data.Message
import edu.ucsc.cse118.assignment3.data.Workspace
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SharedViewModel : ViewModel() {

    private val _workspaces = MutableLiveData<ArrayList<Workspace>>()
    val workspaces: LiveData<ArrayList<Workspace>> = _workspaces

    private val _workspace = MutableLiveData<ViewModelEvent<Workspace>>()
    val workspace : LiveData<ViewModelEvent<Workspace>> = _workspace

    private val _channels = MutableLiveData<ArrayList<Channel>>()
    val channels: LiveData<ArrayList<Channel>> = _channels

    private val _channel = MutableLiveData<ViewModelEvent<Channel>>()
    val channel : LiveData<ViewModelEvent<Channel>> = _channel

    private val _messages = MutableLiveData<ArrayList<Message>>()
    val messages: LiveData<ArrayList<Message>> = _messages

    private val _message = MutableLiveData<ViewModelEvent<Message>>()
    val message : LiveData<ViewModelEvent<Message>> = _message

    private val _member = MutableLiveData<Member>()
    val member : LiveData<Member> = _member

    private val _error = MutableLiveData<ViewModelEvent<String>>()
    val error : LiveData<ViewModelEvent<String>> = _error

    var wid: Workspace? = null
    var cid: Channel? = null

    fun getMember() : Member? {
        return member.value
    }

    fun setCurrWorkspace(newId:  Workspace) {
        wid = newId
    }
    fun getCurrWorkspace(): Workspace? {
        return wid
    }
    fun setCurrChannel(newId:  Channel) {
        cid = newId
    }
    fun getCurrChannel(): Channel? {
        return cid
    }

    fun setWorkspace(value: Workspace) {
        _workspace.value = ViewModelEvent(value)
    }
    fun setChannel(value: Channel) {
        _channel.value = ViewModelEvent(value)
    }
    fun setMessage(value: Message) {
        _message.value = ViewModelEvent(value)
    }
    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _member.postValue(MemberRepository().login(email, password))
            } catch (e: Exception) {
                _error.postValue(ViewModelEvent(e.message.toString()))
            }
        }
    }
    fun getWorkspaces() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _workspaces.postValue(WorkspaceRepository().getAll(member.value))
            } catch (e: Exception) {
                _error.postValue(ViewModelEvent(e.message.toString()))
            }
        }
    }
    fun getChannels(id: String) {
        viewModelScope.launch(Dispatchers.IO) {

            try {
                _channels.postValue(ChannelRepository().getAll(member.value, id))
            } catch (e: Exception) {
                _error.postValue(ViewModelEvent(e.message.toString()))
            }
        }
    }
    fun getMessages(id: String) {
        viewModelScope.launch(Dispatchers.IO) {

            try {
                _messages.postValue(MessageRepository().getAll(member.value, id))
            } catch (e: Exception) {
                _error.postValue(ViewModelEvent(e.message.toString()))
            }
        }
    }
    fun getWorkspace() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _workspace.postValue(ViewModelEvent(WorkspaceRepository().getOne(member.value, _workspace.value?.getRawContent())))
            } catch (e: Exception) {
                _error.postValue(ViewModelEvent(e.message.toString()))
            }
        }
    }
    fun getMessage(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _message.postValue(ViewModelEvent(MessageRepository().getOne(member.value, _message.value?.getRawContent(), id)))
            } catch (e: Exception) {
                _error.postValue(ViewModelEvent(e.message.toString()))
            }
        }
    }
    fun addMessage(club: Message) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                cid?.let { MessageRepository().addOne(member.value, club, it.id) }
                _message.postValue(ViewModelEvent(club))
            } catch (e: Exception) {
                _error.postValue(ViewModelEvent(e.message.toString()))
            }
        }
    }
    fun removeMessage(club: Message) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                MessageRepository().removeOne(member.value, club)
                _message.postValue(ViewModelEvent(club))
            } catch (e: Exception) {
                _error.postValue(ViewModelEvent(e.message.toString()))
            }
        }
    }
    fun addBallClub(club: Workspace) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                WorkspaceRepository().addOne(member.value, club)
                _workspace.postValue(ViewModelEvent(club))
            } catch (e: Exception) {
                _error.postValue(ViewModelEvent(e.message.toString()))
            }
        }
    }

}